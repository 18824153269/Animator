package com.example.animator;

import android.view.View;

/**
 * Created by eluzhu on 2018/1/4.
 */

public class VideoNumber {

    public static int readChangedNumber(String mValidateData, int count) {
        count = count - 1; //传入count为次数，count作用为坐标，因为坐标从0开始，所以要减1
        int number = Integer.parseInt(String.valueOf(mValidateData.charAt(count)));

        if (number == 0) {
            return R.drawable.image_num0_gold;
        }
        if (number == 1) {
            return R.drawable.image_num1_gold;
        }
        if (number == 2) {
            return R.drawable.image_num2_gold;
        }
        if (number == 3) {
            return R.drawable.image_num3_gold;
        }
        if (number == 4) {
            return R.drawable.image_num4_gold;
        }
        if (number == 5) {
            return R.drawable.image_num5_gold;
        }
        if (number == 6) {
            return R.drawable.image_num6_gold;
        }
        if (number == 7) {
            return R.drawable.image_num7_gold;
        }
        if (number == 8) {
            return R.drawable.image_num8_gold;
        }
        if (number == 9) {
            return R.drawable.image_num9_gold;
        }

        return -1;
    }
}
