package Minigame.FinalBossFightPackage;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Stage{
    private int cnt = 0;
    private boolean isSwitch;
    private boolean finished = false;
    private YanKeys  YanKeysArray[], defaultArray[];
    public Stage(boolean isSwitch)
    {
        this.isSwitch = isSwitch;
        defaultArray = new YanKeys[]{new YanKeys(KeyEvent.VK_W,"Image/Mung_df.png", "Image/Mung_hv.png"),
                new YanKeys(KeyEvent.VK_A,"Image/Pun_df.png", "Image/Pun_hv.png"),
                new YanKeys(KeyEvent.VK_S,"Image/Shi_df.png", "Image/Shi_hv.png"),
                new YanKeys(KeyEvent.VK_D,"Image/Tu_df.png", "Image/Tu_hv.png"),
        };
        // 2. แปลงเป็น List เพื่อให้ใช้คำสั่ง Shuffle ได้ง่ายๆ
        ArrayList<YanKeys> list = new ArrayList<>(Arrays.asList(defaultArray));

        // 3. สลับตำแหน่งแบบสุ่ม (เหมือนสับไพ่)
        Collections.shuffle(list);

        // 4. ดึงค่าจาก List ที่สลับแล้วมาใส่ Array ผลลัพธ์
        YanKeysArray = list.toArray(new YanKeys[0]);
        setDefault();
    }

    public YanKeys[] getYanKeysArray() {
        return YanKeysArray;
    }
    public YanKeys[] getSwitchYanKeysArray() {
        YanKeys[] fake;
        ArrayList<YanKeys> list = new ArrayList<>(Arrays.asList(YanKeysArray));
        Collections.shuffle(list);
        fake = list.toArray(new YanKeys[0]);
        return fake;
    }
    public void updateCNT(){
        cnt++;
        if(cnt == YanKeysArray.length){
            finished = true;
        } else{
            YanKeysArray[cnt].setAsTarget();
        }
    }
    public boolean isFinished(){
        return finished;
    }
    public int getCnt(){
        return cnt;
    }
    public boolean isSwitch(){return isSwitch;}
    public void setDefault(){
        for(int i = 0; i < defaultArray.length; i++){
            YanKeysArray[i].setUnactive();
        }
        YanKeysArray[0].setAsTarget();
        cnt = 0;
    }

}
