package net.vladimir.multiframe.modes.dualframe;

public class DualFrameData {

    public int playerSwitch;
    public int playerSpeed;
    public int playerY;
    public int playerSize = 50;
    public int obstacleSwitch;
    public int obstacleHeight;
    public int obstacleGap;
    public int obstacleDistance;
    public int obstacleSpeed;
    public int wallWidth;

    public DualFrameData() {

    }

    public DualFrameData(int playerSwitch, int playerSpeed, int playerY, int playerSize, int obstacleSwitch, int obstacleHeight, int obstacleGap, int obstacleDistance, int obstacleSpeed, int wallWidth) {
        this.playerSwitch = playerSwitch;
        this.playerSpeed = playerSpeed;
        this.playerY = playerY;
        this.playerSize = playerSize;
        this.obstacleSwitch = obstacleSwitch;
        this.obstacleHeight = obstacleHeight;
        this.obstacleGap = obstacleGap;
        this.obstacleDistance = obstacleDistance;
        this.obstacleSpeed = obstacleSpeed;
        this.wallWidth = wallWidth;
    }

    public void copy(DualFrameData data) {
        this.playerSwitch = data.playerSwitch;
        this.playerSpeed = data.playerSpeed;
        this.playerY = data.playerY;
        this.playerSize = data.playerSize;
        this.obstacleSwitch = data.obstacleSwitch;
        this.obstacleHeight = data.obstacleHeight;
        this.obstacleGap = data.obstacleGap;
        this.obstacleDistance = data.obstacleDistance;
        this.obstacleSpeed = data.obstacleSpeed;
        this.wallWidth = data.wallWidth;
    }

}
