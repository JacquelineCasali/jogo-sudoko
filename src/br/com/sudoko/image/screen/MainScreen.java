package br.com.sudoko.image.screen;


import br.com.sudoko.image.button.ButtonGame;
import br.com.sudoko.image.button.ExitButton;
import br.com.sudoko.image.button.ResetButton;
import br.com.sudoko.image.frame.MainFrame;
import br.com.sudoko.image.input.NumberText;
import br.com.sudoko.image.panel.MainPanel;
import br.com.sudoko.image.panel.SudokuSector;
import br.com.sudoko.model.Space;
import br.com.sudoko.service.BoardService;
import br.com.sudoko.service.EventEnum;
import br.com.sudoko.service.NotifierService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.swing.JOptionPane.*;
import static br.com.sudoko.service.EventEnum.CLEAR_SPACE;
//composição da tela
public class MainScreen {

    private final static Dimension dimension=new Dimension(600,600);
    private final BoardService boardService;

    private final NotifierService notifierService;
    private JButton buttonGame;
    private JButton exitButton;
    private JButton resetButton;
    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
this.notifierService=new NotifierService();
    }
// compor a tela
public void buildMainScreen(){
    JPanel mainPanel = new MainPanel(dimension);
    JFrame mainFrame = new MainFrame(dimension, mainPanel);
//
    for (int r = 0; r < 9; r+=3) {
        var endRow = r + 2;
        for (int c = 0; c < 9; c+=3) {
            var endCol = c + 2;
            var spaces = getSpacesFromSector(boardService.getSpaces(), c, endCol, r, endRow);
            JPanel sector = generateSection(spaces);
            mainPanel.add(sector);
        }
    }
addResetButton(mainPanel) ;
        addCheckGameStatusButton(mainPanel);
    addFinishGameButton(mainPanel);
    mainFrame.revalidate();
    mainFrame.repaint();
}


private List<Space> getSpacesFromSector(
        final List<List<Space>> spaces,
        final int initCol, final int endCol,
        final int initRow, final int endRow

){
    List<Space> spaceSector = new ArrayList<>();
    for (int r = initRow; r <= endRow; r++) {
        for (int c = initCol; c <= endCol; c++) {
            spaceSector.add(spaces.get(c).get(r));
        }
    }
    return spaceSector;
}
private JPanel generateSection(final List<Space> spaces){
    List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
   fields.forEach(t->notifierService.subscribe(CLEAR_SPACE,t));
    return new SudokuSector(fields);
}


    private void addFinishGameButton(JPanel mainPanel) {
        exitButton = new ExitButton(e->{
            if (boardService.gameIsFinished()){
                showMessageDialog(null, "Parabéns você concluiu o jogo");
                resetButton.setEnabled(false);
                buttonGame.setEnabled(false);
                exitButton.setEnabled(false);
            } else {
                var message = "Seu jogo tem alguma inconsistência, ajuste e tente novamente";
                showMessageDialog(null, message);
            }
        });

        mainPanel.add(exitButton);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {


        buttonGame = new ButtonGame(e->{
            var hasErrors=boardService.hasErrors();
            var gameStatus=boardService.getStatus();
            var message=switch (gameStatus){
                case NON_STARTED -> "O Jogo não foi iniciado";
                case INCOMPLETE -> "O Jogo está incompleto";
                case COMPLETE -> "Jogo Finalizado";
            };

            message+= hasErrors?" e contém erros": "e não contém erros";
            showMessageDialog(null,message);

        });


        mainPanel.add(buttonGame);
    }

    private void addResetButton(JPanel mainPanel) {
      resetButton = new ResetButton(e -> {
            var dialogResult = showConfirmDialog(
                    null,
                    "Deseja realmente reiniciar o jogo?",
                    "Limpar o jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );
            if (dialogResult == 0){
                boardService.reset();
                    notifierService.notify(CLEAR_SPACE);
                          }

        });


        mainPanel.add(resetButton);
    }



}

