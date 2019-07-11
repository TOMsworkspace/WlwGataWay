package com.example.wlwgateway.tool;
    /*
    数据格式：从高到低
        第一个字节：7E
        第二个字节：设备号（默认为0表示查询设备状态，分为1号路由器，2号路由器,3号路由器）
        第三个字节：功能选择（
            0X10，表示关闭LED1，0X20表示关闭LED2，0X30表示关闭LED3，0X40表示关闭所有灯	LED4;0X11表示打开LED1，0X21表示打开LED2，0X31表示打开LED3，0X41表示打开所有灯
            0X12表示按模式一开喇叭，0X22表示按模式二开喇叭
            0X03表示查询光敏
            0X04表示查询甲烷
            0X05表示查询温度湿度
            ）
        第四个字节-第七个字节：上传数据
        第八个字节：0D

        温湿度：
        数据共4个字节，前面两个为湿度（两个字节的数据相加的和1为最终数据），温度（一个字节是整数部分，后二个字节是小数部分）

        CH4：
        数据按16进制换算

        光敏：
        数据按16进制换算

        蜂鸣器：
        4-7字节均为FF表示歌曲放映完毕

        传感器分配：
        1号传感器对应温湿度
        2号传感器对应光敏和LED灯
        3号传感器对应甲烷和蜂鸣器
     */
public class Command {
    private byte[] command=new byte[8];

    public Command(byte[] com){
        for(int i=0;i< 8;i++) {
            command[i]=com[i];
        }
    }


    public boolean isValid(){
        return command[0] == (byte)0x7e && command[7] == (byte)0x0d;

    }

    public static byte[] getDeviceStataCommand(){
        byte[] comm=new byte[8];
        comm[0]=0x7e;
        comm[1]=0x00;
        comm[2]=0x00;
        comm[3]=0x00;
        comm[4]=0x00;
        comm[5]=0x00;
        comm[6]=0x00;
        comm[7]=0x0d;
        return comm;
    }

    public byte[] getCommand() {
        return command;
    }

    public byte getCommand(int index) {
        return command[index];
    }

    public void setCommand(byte[] command) {
        this.command = command;
    }

}
