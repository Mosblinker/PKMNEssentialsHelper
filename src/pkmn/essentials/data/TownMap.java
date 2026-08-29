/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Mosblinker
 */
public class TownMap extends AbstractPBSElement implements Comparable<TownMap>{
    
    public static final String PBS_FILE = "town_map."+PBSData.PBS_FILE_EXTENSION;
    
    public static final String MAP_IMAGES_LOCATION = "Graphics"+File.separator+"UI"+File.separator+"Town Map";
    
    public static final String[] PROPERTY_NAMES = {
        "Name",
        "Filename",
        "Point"
    };
    
    private int regionNumber = -1;
    
    private String fileName = null;
    
    private List<MapPoint> points = new ArrayList<>();
    
    private File mapFile = null;
    
    public TownMap(){
        super();
    }
    
    public TownMap(int regionNumber){
        super();
        TownMap.this.setRegionNumber(regionNumber);
    }
    
    public TownMap(TownMap map){
        super(map);
        this.regionNumber = map.regionNumber;
        this.fileName = map.fileName;
        for (MapPoint point : map.points)
            this.points.add(new MapPoint(point));
        this.mapFile = map.mapFile;
    }
    
    @Override
    public void setID(String id){
        setRegionNumber(Integer.parseInt(Objects.requireNonNull(id)));
    }
    
    public void setRegionNumber(int regionNumber){
        this.regionNumber = regionNumber;
        super.setID(Integer.toString(regionNumber));
    }
    
    public int getRegionNumber(){
        return regionNumber;
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    
    public List<MapPoint> getPoints(){
        return points;
    }
    
    public File getMapImageFile(){
        return mapFile;
    }
    
    public void setMapImageFile(File file){
        this.mapFile = file;
    }
    @Override
    public String toData(){
        String str = "["+getRegionNumber() + "]";
        if (getName() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[0] + " = " + getName();
        if (getFileName() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[1] + " = " + getFileName();
        for (MapPoint point : getPoints()){
            str += System.lineSeparator()+PROPERTY_NAMES[3] + " = " + point;
        }
        return str;
    }
    @Override
    protected void setProperty(int propertyIndex, String value) {
        switch(propertyIndex){
            case(0):
                setName(value);
                break;
            case(1):
                setFileName(value);
                break;
            case(2):
                getPoints().add(MapPoint.getMapPoint(value));
        }
        if (propertyIndex == getPropertyNames().length)
            setID(value);
    }

    @Override
    protected String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }
    
    @Override
    protected void reset(){
        super.reset();
        regionNumber = -1;
        fileName = null;
        points.clear();
        mapFile = null;
    }

    @Override
    public int compareTo(TownMap o) {
        return Integer.compare(regionNumber, o.regionNumber);
    }
    
    public static class MapPoint implements Comparable<MapPoint>{
        
        public static MapPoint getMapPoint(String text){
            String[] values = text.split(",");
            int x = Integer.parseInt(values[0].trim());
            int y = Integer.parseInt(values[1].trim());
            if (values.length == 2)
                return new MapPoint(x,y);
            MapPoint point = new MapPoint(x,y,values[2].trim());
            if (values.length > 3)
                point.setPointOfInterest(values[3].trim());
            if (values.length > 4 && !values[4].isBlank() && !values[5].isBlank() && !values[6].isBlank()){
                point.setFlyDestination(Integer.parseInt(values[5].trim()), 
                        Integer.parseInt(values[6].trim()), 
                        Integer.parseInt(values[4].trim()));
            }
            if (values.length > 7 && !values[7].isBlank())
                point.setGameSwitch(Integer.valueOf(values[7].trim()));
            return point;
        }
        
        private int x;
        
        private int y;
        
        private String name = null;
        
        private String poi = null;
        
        private Integer flyMap = null;
        
        private Integer flyX = null;
        
        private Integer flyY = null;
        
        private Integer gameSwitch = null;
        
        public MapPoint(int x, int y){
            if (x < 0 || y < 0)
                throw new IllegalArgumentException();
            this.x = x;
            this.y = y;
        }
        
        public MapPoint(int x, int y, String name){
            this(x,y);
            this.name = name;
        }
        
        public MapPoint(MapPoint point){
            this(point.x,point.y,point.name);
            this.poi = point.poi;
            this.flyMap = point.flyMap;
            this.flyX = point.flyX;
            this.flyY = point.flyY;
            this.gameSwitch = point.gameSwitch;
        }
        
        public int getX(){
            return x;
        }
        
        public void setX(int x){
            if (x < 0)
                throw new IllegalArgumentException();
            this.x = x;
        }
        
        public int getY(){
            return y;
        }
        
        public void setY(int y){
            if (y < 0)
                throw new IllegalArgumentException();
            this.y = y;
        }
        
        public void setPosition(int x, int y){
            if (x < 0 || y < 0)
                throw new IllegalArgumentException();
            setX(x);
            setY(y);
        }
        
        public String getName(){
            return name;
        }
        
        public void setName(String name){
            this.name = name;
        }
        
        public String getPointOfInterest(){
            return poi;
        }
        
        public void setPointOfInterest(String poi){
            this.poi = poi;
        }
        
        public Integer getFlyDestinationMapID(){
            return flyMap;
        }
        
        public void setFlyDestinationMapID(Integer mapID){
            if (mapID != null && mapID < 0)
                throw new IllegalArgumentException();
            flyMap = mapID;
        }
        
        public Integer getFlyDestinationX(){
            return flyX;
        }
        
        public void setFlyDestinationX(Integer x){
            if (x != null && x < 0)
                throw new IllegalArgumentException();
            flyX = x;
        }
        
        public Integer getFlyDestinationY(){
            return flyY;
        }
        
        public void setFlyDestinationY(Integer y){
            if (y != null && y < 0)
                throw new IllegalArgumentException();
            flyY = y;
        }
        
        public void setFlyDestination(int x, int y){
            if (x < 0 || y < 0)
                throw new IllegalArgumentException();
            setFlyDestinationX(x);
            setFlyDestinationY(y);
        }
        
        public void setFlyDestination(int x, int y, int mapID){
            if (x < 0 || y < 0 || mapID < 0)
                throw new IllegalArgumentException();
            setFlyDestinationMapID(mapID);
            setFlyDestination(x,y);
        }
        
        public void clearFlyDestination(){
            setFlyDestinationX(null);
            setFlyDestinationY(null);
            setFlyDestinationMapID(null);
        }
        
        public Integer getGameSwitch(){
            return gameSwitch;
        }
        
        public void setGameSwitch(Integer s){
            if (s != null && s < 0)
                throw new IllegalArgumentException();
            gameSwitch = s;
        }
        
        @Override
        public String toString(){
            return getX() + ","+
                    getY()+","+
                    Objects.toString(getName(),"")+","+
                    Objects.toString(getPointOfInterest(),"")+","+
                    Objects.toString(getFlyDestinationMapID(),"")+","+
                    Objects.toString(getFlyDestinationX(),"")+","+
                    Objects.toString(getFlyDestinationY(),"")+","+
                    Objects.toString(getGameSwitch(),"");
        }
        @Override
        public boolean equals(Object obj){
            if (obj == this)
                return true;
            else if (!(obj instanceof MapPoint))
                return false;
            MapPoint point = (MapPoint) obj;
            return point.x == x && point.y == y;
        }
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + this.x;
            hash = 79 * hash + this.y;
            return hash;
        }

        @Override
        public int compareTo(MapPoint o) {
            if (x == o.x)
                return Integer.compare(y, o.y);
            return Integer.compare(x, o.x);
        }
    }
}
