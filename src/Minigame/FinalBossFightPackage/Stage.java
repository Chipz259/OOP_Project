package Minigame.FinalBossFightPackage;

public class Stage{
    private int cnt = 0;
    private boolean finished = false;
    private YanKeys[] YanKeysArray;
    public Stage(YanKeys[] YanKeysArray)
    {
        this.YanKeysArray = YanKeysArray;
    }

    public YanKeys[] getYanKeysArray() {
        return YanKeysArray;
    }
    public void updateCNT(){
        cnt++;
        if(cnt == YanKeysArray.length){
            finished = !finished;
        }
    }
    public void resetCNT(){
        cnt = 0;
    }
    public boolean isFinished(){
        return finished;
    }
    public int getCnt(){
        return cnt;
    }
}
