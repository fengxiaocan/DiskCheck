package com.n.disk;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.n.disk.common.Logger;
import com.n.disk.common.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DiskUi extends JPanel {
    public static final long _500MB = 500 * 1024L * 1024L;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JComboBox selectBox;
    private JComboBox blockBox;
    private JComboBox bufferBox;
    private JButton bt1;
    private JButton bt2;
    private JButton bt3;
    private JButton btStop;
    private JTextArea logcat;
    private JLabel leftLab;
    private JLabel rightLab;
    private JProgressBar progressBar;
    private JButton btClear;
    private JCheckBox checkBox;
    private JButton btClearTemp;
    private PlateInfo plate;
    private int blockSize = 500;
    private int bufferSize = 8;
    private DiskSpeed diskSpeed;
    private CheckPlate checkPlate;

    public DiskUi() {
        initComponents();
        initData();
    }

    public static void main(String[] args) {
        int width = 600;
        int height = 300;

        JFrame dialog = new JFrame("U盘快速扩容检测");
        DiskUi diskUi = new DiskUi();
        dialog.setMinimumSize(new Dimension(width, height));
        dialog.add(diskUi);
        dialog.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - height / 2, width, height);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(dialog, diskUi);
            }
        });
        dialog.pack();
        dialog.setVisible(true);
    }

    /**
     * 关闭窗口
     */
    private static void onCancel(JFrame dialog, DiskUi parentComponent) {
        // add your code here if necessary
        int confirmDialog = JOptionPane.showConfirmDialog(parentComponent, "是否确认退出?", "提示", JOptionPane.YES_NO_OPTION);
        //如果这个整数等于JOptionPane.YES_OPTION，则说明你点击的是“确定”按钮，则允许继续操作，否则结束
        if (confirmDialog == JOptionPane.YES_OPTION) {
            parentComponent.onDestroy();
            dialog.dispose();
            System.exit(0);
        }
    }

    private void initData() {
        progressBar.setMaximum(100);
        DiskUtils.startPlateListener(list -> {
            plate = null;
            selectBox.removeAllItems();
            if (list != null) {
                for (PlateInfo info : list) {
                    if (selectBox != null) selectBox.addItem(info);
                }
            }
        });
        selectBox.addItemListener(e -> {
            plate = (PlateInfo) e.getItem();
            Logger.error("选择U盘:" + plate.getName());
            onSelect();
        });
        blockBox.addItem(new MbSize("500MB", 500));
        blockBox.addItem(new MbSize("300MB", 300));
        blockBox.addItem(new MbSize("200MB", 200));
        blockBox.addItem(new MbSize("100MB", 100));
        blockBox.addItem(new MbSize("50MB", 50));
        blockBox.addItem(new MbSize("800MB", 800));
        blockBox.addItem(new MbSize("1GB", 1024));
        blockBox.addItem(new MbSize("2GB", 2048));

        bufferBox.addItem(new KbSize("8KB", 8));
        bufferBox.addItem(new KbSize("1KB", 1));
        bufferBox.addItem(new KbSize("2KB", 2));
        bufferBox.addItem(new KbSize("4KB", 4));
        bufferBox.addItem(new KbSize("12KB", 12));
        bufferBox.addItem(new KbSize("16KB", 16));
        bufferBox.addItem(new KbSize("24KB", 24));
        bufferBox.addItem(new KbSize("32KB", 32));

        blockBox.addItemListener(e -> {
            blockSize = ((MbSize) e.getItem()).size;
        });
        bufferBox.addItemListener(e -> {
            bufferSize = ((KbSize) e.getItem()).size;
        });
        bt1.addActionListener(e -> {
            if (diskSpeed != null) {
                if (diskSpeed.isRunning()) {
                    printMsg("已经正在执行读写速度测试，请不要测试其他功能！！！");
                    return;
                }
            }
            allEnable(false);
            diskSpeed = new DiskSpeed(blockSize, bufferSize, plate.getPath(), new DiskSpeed.OnSpeedListener() {

                @Override
                public void onProgress(boolean isWrite, long usedTime, long speed, float progress) {
                    int value = (int) (progress * 100);
                    progressBar.setValue(value);
                    leftLab.setText(StringUtil.formatMusicTime(usedTime));
                    rightLab.setText(StringUtil.formatFileSize(speed) + "/S");
                }

                @Override
                public void onWriteFinish(long usedTime, long maxSpeed, long averageSpeed) {
                    leftLab.setText(StringUtil.formatMusicTime(usedTime));
                    rightLab.setText("写入速度:" + StringUtil.formatFileSize(averageSpeed) + "/S");
                    printMsg("写入速度测试完成，U盘大小为：" + plate.getTotalSpaceStr());
                    printMsg(StringUtil.join("测试用时:" , (usedTime/1000),"秒",usedTime%1000,"毫秒"));
                    printMsg("最大写入速度:" + StringUtil.formatFileSize(maxSpeed)+"/S");
                    printMsg("平均写入速度:" + StringUtil.formatFileSize(averageSpeed)+"/S");
                }

                @Override
                public void onReadFinish(long usedTime, long maxSpeed, long averageSpeed) {
                    leftLab.setText(StringUtil.formatMusicTime(usedTime));
                    rightLab.setText("读取速度:" + StringUtil.formatFileSize(averageSpeed) +"/S");
                    printMsg("读取速度测试完成，U盘大小为：" + plate.getTotalSpaceStr());
                    printMsg(StringUtil.join("测试用时:" , (usedTime/1000),"秒",usedTime%1000,"毫秒"));
                    printMsg("最大读取速度:" + StringUtil.formatFileSize(maxSpeed)+"/S");
                    printMsg("平均读取速度:" + StringUtil.formatFileSize(averageSpeed)+"/S");

                    allEnable(true);
                    if (checkBox.isSelected()) {
                        DiskUtils.deleteDiskTempFile(plate.getPath());
                    }
                }

                @Override
                public void onError(String error) {
                    printMsg(error);
                    allEnable(true);
                }
            });
            printMsg("开始执行读写速度测试!");
            diskSpeed.start();
        });
        //快速扩容测试
        bt2.addActionListener(e -> {
            progressBar.setValue(0);
            leftLab.setText("");
            rightLab.setText("");
            allEnable(false);

            setMsg("快速扩容测试开始!");
            new FastCheck(plate.getPath(), blockSize * 1024L * 1024L, new FastCheck.OnCheckPlateListener() {
                @Override
                public void onError(Exception e) {
                    printMsg("快速扩容失败!!!!");
                    printMsg(e);
                    allEnable(true);
                }

                @Override
                public void onFinish(long usedTime) {
                    printMsg("快速扩容测试完成，U盘容量正常，容量大小为：" + plate.getTotalSpaceStr() + " 测试用时: " + usedTime + " 毫秒");
                    allEnable(true);
                }
            }).start();
        });
        //数据完整性测试
        bt3.addActionListener(e -> {
            if (plate == null) return;
            if (checkPlate != null) {
                if (checkPlate.isRunning()) {
                    setMsg("数据完整性校验测试正在进行!!!请不要重复操作");
                    return;
                }
            }
            progressBar.setValue(0);
            leftLab.setText("");
            rightLab.setText("");
            allEnable(false);

            setMsg("开始执行数据完整性校验测试!");
            checkPlate = new CheckPlate(plate.getPath(), blockSize * 1024L * 1024L,bufferSize, new CheckPlate.OnProgressListener() {
                private int mProgress;

                @Override
                public void onProgress(long speed, long usableTime, float progress) {
                    int value = (int) (progress * 100);
                    progressBar.setValue(value);
                    if (mProgress != value) {
                        mProgress = value;
                        long totalTime = (long) (usableTime / progress / 1000);
                        long l = usableTime / 1000;
                        printMsg(StringUtil.join("进度:", value, "%", " 用时:", l / 60, "分", l % 60, "秒 预计总用时:",
                                totalTime / 60, "分", totalTime % 60, "秒 预计剩余用时:", (totalTime - l) / 60,
                                "分", (totalTime - l) % 60, "秒"));
                    }
                    leftLab.setText(StringUtil.formatMusicTime(usableTime));
                    rightLab.setText(StringUtil.formatFileSize(speed) + "/S");
                }

                @Override
                public void onError(int code, String error) {
                    printMsg(error);
                    allEnable(true);
                }

                @Override
                public void onWriting() {
                    printMsg("正在进行写入操作!!");
                }

                @Override
                public void onChecking() {
                    printMsg("写入操作完成，正在进行读写校验操作!");
                }

                @Override
                public void onFinish() {
                    printMsg("数据完整性校验测试完成！U盘没有问题！");
                    allEnable(true);
                    if (checkBox.isSelected()) {
                        DiskUtils.deleteDiskTempFile(plate.getPath());
                    }
                }
            });
            checkPlate.start();
        });
        btStop.addActionListener(e -> {
            if (plate == null) return;
            if (diskSpeed != null) {
                diskSpeed.stop();
                diskSpeed = null;
            }
            if (checkPlate != null) {
                checkPlate.stop();
                checkPlate = null;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            allEnable(true);
        });
        btClear.addActionListener(e -> {
            DiskUtils.clearPlate(plate.getPath());
            printMsg("已清理U盘所有文件!");
        });
        btClearTemp.addActionListener(e -> {
            DiskUtils.deleteDiskTempFile(plate.getPath());
            printMsg("已清理U盘临时测试文件!");
        });
    }

    private void allEnable(boolean isEnabled) {
        setEnable(bt1, isEnabled);
        setEnable(bt2, isEnabled);
        setEnable(bt3, isEnabled);
        setEnable(btClear, isEnabled);
        setEnable(btClearTemp, isEnabled);
    }

    private void setMsg(String msg) {
        logcat.setText(msg);
        logcat.append("\r\n");
    }

    private void printMsg(String msg) {
        logcat.append(msg);
        logcat.append("\r\n");
    }

    private void printMsg(Throwable e) {
        printMsg(e.getMessage());
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement traceElement : stackTrace) {
            printMsg("Class:" + traceElement.getClassName() + "  Method:" + traceElement.getMethodName());
        }
    }

    private void setEnable(JComponent view, boolean isEnabled) {
        view.setEnabled(isEnabled);
    }

    private void onSelect() {
        allEnable(plate != null);
        setEnable(btStop, plate != null);
    }

    private void onDestroy() {
        DiskUtils.stopPlateListener();
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        var panel1 = new JPanel();
        var label3 = new JLabel();
        var label4 = new JLabel();
        var label5 = new JLabel();
        selectBox = new JComboBox();
        blockBox = new JComboBox();
        bufferBox = new JComboBox();
        var panel2 = new JPanel();
        bt1 = new JButton();
        bt2 = new JButton();
        bt3 = new JButton();
        btClear = new JButton();
        btClearTemp = new JButton();
        checkBox = new JCheckBox();
        btStop = new JButton();
        var scrollPane1 = new JScrollPane();
        logcat = new JTextArea();
        leftLab = new JLabel();
        rightLab = new JLabel();
        progressBar = new JProgressBar();
        logcat.setEditable(false);
        //======== this ========
        setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));

        //======== panel1 ========
        {
            panel1.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));

            //---- label3 ----
            label3.setText("选择U盘");
            panel1.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
            panel1.add(selectBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

            //---- label3 ----
            label4.setText("读写块大小");
            panel1.add(label4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
            panel1.add(blockBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

            //---- label3 ----
            label5.setText("缓冲区大小");
            panel1.add(label5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
            panel1.add(bufferBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

        }
        add(panel1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

        //======== panel2 ========
        {
            panel2.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));

            //---- bt1 ----
            bt1.setText("\u8bfb\u5199\u901f\u5ea6\u6d4b\u8bd5");
            panel2.add(bt1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

            //---- bt2 ----
            bt2.setText("\u6269\u5bb9\u6d4b\u8bd5");
            panel2.add(bt2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

            //---- bt3 ----
            bt3.setText("\u6570\u636e\u5b8c\u6574\u6027\u6821\u9a8c");
            panel2.add(bt3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

            //---- btStop ----
            btStop.setText("\u505c\u6b62");
            panel2.add(btStop, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

            //---- btClear ----
            btClear.setText("\u6e05\u7a7aU\u76d8");
            panel2.add(btClear, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

            //---- btClearTemp ----
            btClearTemp.setText("\u6e05\u7406\u6d4b\u8bd5\u6587\u4ef6");
            panel2.add(btClearTemp, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
            //---- checkBox ----
            checkBox.setText("自动清理临时文件");
            panel2.add(checkBox, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        }
        add(panel2, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

        //======== scrollPane1 ========
        {

            //---- logcat ----
            scrollPane1.setViewportView(logcat);
        }
        add(scrollPane1, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null));

        //---- leftLabel ----
        add(leftLab, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));

        //---- label4 ----
        add(rightLab, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        add(progressBar, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private int getContentWidth() {
        return getMinimumSize().width;
    }

    private int getContentHeight() {
        return getMinimumSize().height;
    }

    public static class MbSize {
        String name;
        int size;

        public MbSize(String name, int size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    public static class KbSize {
        String name;
        int size;

        public KbSize(String name, int size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
