package edu.hitsz.application;

import edu.hitsz.scorerecord.ScoreBoardService;
import edu.hitsz.scorerecord.ScoreBoardTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreBoardView {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JScrollPane tableScrollPanel;
    private JLabel headerLabel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JButton returnButton;

    public ScoreBoardView(ScoreBoardService scoreBoardService) {

        ScoreBoardTableModel model = new ScoreBoardTableModel(scoreBoardService);
        scoreBoardService.addObserver(model);

        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(deleteButton, "请先选择要删除的记录！");
                    return;
                }

                String noValueStr = (String) scoreTable.getValueAt(row, 0);
                int numberOfRecord = Integer.parseInt(noValueStr);

                int result = JOptionPane.showConfirmDialog(deleteButton,
                        "是否确定中删除？");
                if (JOptionPane.YES_OPTION == result) {
                    model.removeRow(row);
                    scoreBoardService.deleteRecordByNo(numberOfRecord);
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardLayout.show(Main.cardPanel, Main.START_MENU_VIEW);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
