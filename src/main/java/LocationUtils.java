import au.com.bytecode.opencsv.CSVReader;
import bean.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//id,pid,name,deep,lat,lng,isz
//id:<=3级：(\d\d){3}
//   4级：(\d\d){3}(\d\d\d)
class LocationUtils{
    public static void main(String[] args) throws IOException {
        LocationUtils locationUtils=new LocationUtils("sys_area.csv");
        Location location1=locationUtils.getLocation("北京市丰台区右安门街道XXX");
        System.out.println(location1.getLat()+":"+location1.getLng());
        Location location2=locationUtils.getLocation("吉林");
        System.out.println(location2.getLat()+":"+location2.getLng());
    }
    Map<String, Location> locationIdLocationMap =new HashMap<String, Location>();//id,location
    Map<String,List<String>> locationParentChilesMap =new HashMap<String, List<String>>();//pid,ids
    public LocationUtils(String fileName) throws IOException {
        CSVReader csvReader=new CSVReader(new InputStreamReader(LocationUtils.class.getResourceAsStream(fileName)));
        String[] line;
        while((line=csvReader.readNext())!=null){
            Location location=new Location(line[0],line[1],line[2],line[3],line[4],line[5],line[6]);
            locationIdLocationMap.put(location.getId(),location);
        }
        for(Map.Entry<String, Location> id_location: locationIdLocationMap.entrySet()){
            String pid=id_location.getValue().getPid();
            if(locationParentChilesMap.get(pid)==null){
                locationParentChilesMap.put(pid,new ArrayList<String>());
            }
            locationParentChilesMap.get(pid).add(id_location.getKey());
        }
    }
    public Location getLocation(String path){
        Location result=null;
        String pid="0";
        int index=0;
        boolean flag=true;
        while(flag&&locationParentChilesMap.containsKey(pid)){
            flag=false;
            for(String id: locationParentChilesMap.get(pid)){
                Location location;
                if((location=locationIdLocationMap.get(id))!=null){
                    String name = location.getName();
                    if (path.substring(index).startsWith(name)) {
                        flag=true;
                        index+=name.length();
                        if (path.length()-index == 0 || location.getId().length() == 9) {
                            result=location;
                        } else {
                            pid=id;
                        }
                        break;
                    }
                }
            }
        }
        return result;
    }
}
