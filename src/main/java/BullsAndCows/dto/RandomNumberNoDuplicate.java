package BullsAndCows.dto;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;


public class RandomNumberNoDuplicate {

    public String getRandomNumber() {
        Random randNum = new Random();
        Set<Integer> set = new LinkedHashSet<Integer>();

        while (set.size() < 4) {
            set.add(randNum.nextInt(10));
        }

        String value = "";
        for(Integer i: set){
            value += i;
        }
        return value;
    }
}
