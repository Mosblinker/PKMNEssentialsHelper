/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.rxdata;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import pkmn.essentials.Table;

/**
 *
 * @author Mosblinker
 * @author GamingLiamStudios
 */
public class DecodingTests {
    /**
     * The version of the files to read
     */
    public static final int VERSION_NUMBER = 0x0408;
    
    public static boolean decodeTest(File file) throws IOException{
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        
        int version = in.read();
        version <<= 8;
        version |= in.read();
        
        // If the file version is invalid
        if (version != VERSION_NUMBER){
            System.out.printf("Invalid Version: 0x%04X%n",version);
            return false;
        }
        
        System.out.println("Parsing");
        
        Object parse = parse(in);
        
        // If the file is the scripts file
        if (file.getName().equals("Scripts.rxdata") || file.getName().equals("xScripts.rxdata")){
            System.out.println("Script file detected");
            
        } else {
            
            
        }
        in.close();
        return true;
    }
    
    public static Object parse(InputStream in) throws IOException{
        return parse(in,new ArrayList<>(),new ArrayList<>());
    }
    
    private static String getClassName(Object obj){
        return (obj!=null)?obj.getClass().getName():"null";
    }
    
    private static Object parse(InputStream in, List<Object> objectCache, List<List<Byte>> symbolCache) throws IOException{
        int type = in.read();
        if (type < 0)
            throw new IOException("Unexpected EOF");
        
        switch(type){
            case((int)'@'):
                int index = read_fixnum(in);
                return objectCache.get(index - 1);
            case((int)'I'):     // IVar
                return readIVar(in,objectCache,symbolCache);
            case((int)'e'):     // Extended
                throw new UnsupportedOperationException("Not yet implemented: Extended");
            case((int)'C'):     // UClass
                throw new UnsupportedOperationException("Not yet implemented: UClass");
            case((int)'0'):     // Nil
                return null;
            case((int)'T'):     // True
                return true;
            case((int)'F'):     // False
                return false;
            case((int)'i'):     // Fixnum
                return read_fixnum(in);
            case((int)'f'):     // Float
                {
                    double f = readFloat(in);
                    objectCache.add(f);
                    return f;
                }
            case((int)'l'):     // Bignum
                {
                    BigInteger temp = readBignum(in);
                    objectCache.add(temp);
                    return temp;
                }
            case((int)'\"'):    // String
                {
                    String str = readString(in);
                    objectCache.add(str);
                    return str;
                }
            case((int)'/'):     // RegExp
                throw new UnsupportedOperationException("Not yet implemented: RegExp");
            case((int)'['):     // Array
                return readArray(in,objectCache,symbolCache);
            case((int)'{'):     // Hash
                return readHash(in,objectCache,symbolCache);
            case((int)'}'):     // HashDef
                throw new UnsupportedOperationException("Not yet implemented: HashDef");
            case((int)'S'):     // Struct
                throw new UnsupportedOperationException("Not yet implemented: Struct");
            case((int)'u'):     // UserDef
                return readUserDef(in,objectCache,symbolCache);
        }
        
    }
    
    private static final long NEGATIVE_FIXNUM_MASK = (~(0xff << 24)) & 0xFFFFFFFF;
    
    private static int read_fixnum(InputStream in) throws IOException{
        long x = 0;
        int c = in.read();
        if (c < 0)
            throw new IOException("Unexpected EOF");
        
        byte cByte = (byte) c;
        if (c == 0)
            return 0;
        if (cByte > 0){
            if (4 < c && c < 128)
                return c - 5;
            if (c > Integer.BYTES)
                throw new IOException("Fixnum too big: " + c);
            for (int i = 0; i < 4; i++){
                x >>= 8;
                if (i < c){
                    int temp = in.read();
                    if (temp < 0)
                        throw new IOException("Unexpected EOF");
                    x |= temp << 24;
                }
            }
        } else {
            if (-129 < cByte && cByte < -4)
                return cByte+5;
            c = -cByte;
            if (c > Integer.BYTES)
                throw new IOException("Fixnum too big: " + c);
            x = Integer.toUnsignedLong(-1);
            for (int i = 0; i < 4; i++){
                x = (x >> 8) & NEGATIVE_FIXNUM_MASK;
                if (i < c){
                    int temp = in.read();
                    if (temp < 0)
                        throw new IOException("Unexpected EOF");
                    x |= temp << 24;
                } else 
                    x |= 0xFF;
            }
        }
        return (int) x;
    }
    
    private static String getString(Object obj, String msg) throws IOException{
        if (obj instanceof String)
            return (String) obj;
        if (obj instanceof List<?>){
            List<?> l  = (List<?>) obj;
            byte[] b = new byte[l.size()];
            for (int j = 0; j < l.size(); j++){
                if (l.get(j) instanceof Byte)
                    b[j] = (byte) l.get(j);
                else
                    throw new IOException(msg + getClassName(obj) + "<"+getClassName(l.get(j))+">");
            }
            return new String(b);
        }
        throw new IOException(msg + getClassName(obj));
    }
    
    private static Object readIVar(InputStream in, List<Object> objectCache, List<List<Byte>> symbolCache) throws IOException{
        Object temp = parse(in,objectCache,symbolCache);
        if (!(temp instanceof String))
            throw new IOException("Unsupported IVar Type: " + getClassName(temp));
        String name = (String) temp;
        int length = read_fixnum(in);
        objectCache.add(name);

        int index = objectCache.size() - 1;
        for (int i = 0; i < length; i++){
            String key;
            Object aKey = parse(in,objectCache,symbolCache);
            key = getString(aKey,"IVar key not symbol: ");
            Object value = parse(in,objectCache,symbolCache);
        }
        return objectCache.get(index);
    }
    
    private static double readFloat(InputStream in) throws IOException{
        String str = readString(in);
        
        if (str.equals("nan"))
            return Double.NaN;
        if (str.equals("inf"))
            return Double.POSITIVE_INFINITY;
        if (str.equals("-inf"))
            return Double.NEGATIVE_INFINITY;
        return Double.parseDouble(str);
    }
    
    private static BigInteger readBignum(InputStream in) throws IOException{
        int sign = in.read();
        if (sign < 0)
            throw new IOException("Unexpected EOF");
        int len = read_fixnum(in);
        
        byte[] data = new byte[len * 2];
        int readLen = in.read(data);
        if (readLen < data.length)
            throw new IOException("Unexpected EOF");
        
        byte[] temp = new byte[data.length];
        for (int i = 0; i < data.length; i++){
            temp[i] = data[data.length-1-i];
        }
        return new BigInteger(temp);
    }
    
    private static String readString(InputStream in) throws IOException{
        int strLen = read_fixnum(in);
        byte[] bytes = new byte[strLen];
        int readLength = in.read(bytes);
        if (readLength < strLen)
            throw new IOException("Unexpected EOF");
        return new String(bytes);
    }
    
    private static List<Object> readArray(InputStream in, List<Object> objectCache, List<List<Byte>> symbolCache) throws IOException{
        List<Object> arr = new ArrayList<>();
        int len = read_fixnum(in);
        objectCache.add(arr);
        for (int i = 0; i < len; i++)
            arr.add(parse(in,objectCache,symbolCache));
        return arr;
    }
    
    private static Map<Integer, Object> readHash(InputStream in, List<Object> objectCache, List<List<Byte>> symbolCache) throws IOException{
        Map<Integer,Object> map = new LinkedHashMap<>();
        int length = read_fixnum(in);
        objectCache.add(map);
        for (int i = 0; i < length; i++){
            Object temp = parse(in,objectCache,symbolCache);
            if (!(temp instanceof Integer))
                throw new IOException("Hash key not Fixnum: " + getClassName(temp));
            Integer key = (Integer) temp;
            Object value = parse(in,objectCache,symbolCache);
            map.put(key, value);
        }
        
        return map;
    }
    
    private static long parseLong(byte[] arr){
        long temp = 0;
        for (int j = arr.length-1; j >= 0; j--){
            temp <<= 8;
            temp |= Byte.toUnsignedLong(arr[j]);
        }
        return temp;
    }
    
    private static Object readUserDef(InputStream in, List<Object> objectCache, List<List<Byte>> symbolCache) throws IOException{
        String name;
        Object pname = parse(in,objectCache,symbolCache);
        if (pname instanceof List<?> || pname instanceof String)
            name = getString(pname,"UserDef name not symbol: ");
        else
            throw new IOException("UserDef name not symbol");
        int size = read_fixnum(in);
        if (name.equals("Color")){
            double[] comp = new double[4];
            byte[] arr = new byte[Double.BYTES];
            for (int i = 0; i < comp.length; i++){
                int l = in.read(arr);
                if (l < arr.length)
                    throw new IOException("Unexpected EOF");
                comp[i] = Double.longBitsToDouble(parseLong(arr));
            }
            RPGColor color = new RPGColor(comp[0],comp[1],comp[2],comp[3]);
            objectCache.add(color);
            return color;
        } else if (name.equals("Table")){
            
            Table<Integer> table = new Table();
        }
        
    }
    
}
