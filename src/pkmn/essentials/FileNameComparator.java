/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

import java.io.File;
import java.util.Comparator;

/**
 *
 * @author Mosblinker
 */
public class FileNameComparator implements Comparator<File>{
    
    private int getEndIndexOfNumber(String str, int index){
        while (Character.isDigit(str.charAt(index)))
            index++;
        return index;
    }
    
    private Integer getNumber(String str, int beginIndex, int i){
        try{
            return Integer.valueOf(str.substring(beginIndex, getEndIndexOfNumber(str,i)));
        } catch (NumberFormatException ex){
            return null;
        }
    }
    
    private int indexOfDifference(String str1, String str2, int index){
        for (int i = index; i < str1.length() && i < str2.length(); i++){
            if (str1.charAt(i) != str2.charAt(i))
                return i;
        }
        return -1;
    }

    @Override
    public int compare(File o1, File o2) {
        String name1 = o1.toString();
        String name2 = o2.toString();
        int i = indexOfDifference(name1,name2,0);
        if (i < 0 || (!Character.isDigit(name1.charAt(i)) && !Character.isDigit(name2.charAt(i))))
            return o1.compareTo(o2);
        int start = i;
        while (Character.isDigit(name1.charAt(start-1)))
            start--;
        Integer num1 = getNumber(name1,start,i);
        Integer num2 = getNumber(name2,start,i);
        if (num1 == null || num2 == null || num1.equals(num2))
            return o1.compareTo(o2);
        return num1.compareTo(num2);
    }
    
}
