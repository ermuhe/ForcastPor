package KMeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;  
import java.util.Random;

 
  
/**  
 * k-Means算法，聚类算法；  
 * 实现步骤：
 * 1. 首先是随机获取总体中的K个元素作为总体的K个中心；  
 * 2. 接下来对总体中的元素进行分类，每个元素都去判断自己到K个中心的距离，并归类到最近距离中心去；  
 * 3. 计算每个聚类的平均值，并作为新的中心点  
 * 4. 重复2，3步骤，直到这k个中心点不再变化（收敛了），或执行了足够多的迭代  
 */  
public class KMeans {  
	
    private  double[][] DATA ;
    public int k;                 //k个中心点  
    public int[] memberShip;      //匹配關係
    public double[][] centersIndex;  
    public double[][] centers;    //中心值
    public int[] elementsInCenters;  //聚類后每類元素
    public String lines; 
    public List<String> list;
   
    public KMeans(int k) {  
        this.k = k;  
        File source =new File("G:\\counter.txt");
        int line=0;
        list =new ArrayList<>();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
    	try {
			 inputStream=new FileInputStream(source);
			 bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
			while(null!=(lines=bufferedReader.readLine())){
				list.add(lines);
				line++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(inputStream!=null&&bufferedReader!=null){
				try {
					inputStream.close();
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
    	int fileds=list.get(0).split(",").length;
    	DATA=new double[list.size()][fileds];
    	for(int i=0;i<list.size();i++){
    		for(int j=0;j<fileds;j++){
    		if(j==0){
    		SimpleDateFormat dateformate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		String data=list.get(i).split(",")[j];
    		Date date;
			try {
				date = dateformate.parse(data);
				double timestamp=date.getTime();
	    	    DATA[i][j]=(double) timestamp/Double.MAX_VALUE;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	   
    		}else {
    			DATA[i][j]=Double.parseDouble(list.get(i).split(",")[j]);
			}
    	}
    	}
    }  
  
    /*
     * 计算两点之间的哈曼顿距离，也可采用欧几里得距离 
     */
    public double manhattanDistince(double []paraFirstData,double []paraSecondData){  
        double tempDistince = 0;  
        if((paraFirstData!=null && paraSecondData!=null) && paraFirstData.length==paraSecondData.length){  
            for(int i=0;i<paraFirstData.length;i++){  
                tempDistince += Math.abs(paraFirstData[i] - paraSecondData[i]);  
            }  
        }else{  
            System.out.println("firstData 与 secondData 数据结构不一致");  
        }  
        return tempDistince;  
    }  
     
    /*
     * 随机取K个点
     */
    public void randomCenters(){  
        centersIndex = new double[k][];  
        Random random = new Random();  
        Map map = new HashMap();  
        for(int i=0;i<k;i++){  
            int index = Math.abs(random.nextInt())%DATA.length;  
            if(map.containsKey(index)){  
                i--;  
            }else{  
                //将中心点的下标存到MAP中，保证下次选出的中心点不是同一个  
                map.put(index, DATA[index]);  
                //将中心点的下标存入centers[]中  
                centersIndex[i] = DATA[index];  
            }  
        }  
    }  
      
    /*
     * 匹配每点到K个点的映射
     */
//    public void calMemberShip(){  
//        memberShip = new int[DATA.length];  
//        elementsInCenters = new int[k];  
//        for(int j=0;j<DATA.length;j++){  
//            double currentDistance = Double.MAX_VALUE;  
//            int currentIndex = -1;  
//            double[] item = DATA[j];  
//            for(int i=0;i<k;i++){  
//                //中心点  
//                double[] tempCentersValue = DATA[centersIndex[i]];  
//                double distance = this.manhattanDistince(item, tempCentersValue);  
//                if(distance<currentDistance){  
//                    currentDistance = distance;  
//                    currentIndex = i;  
//                }  
//            }  
//            memberShip[j] = currentIndex;  
//        }  
//          
//        for(int i=0;i<memberShip.length;i++){  
//            elementsInCenters[memberShip[i]]++;  
//        }  
//    }  
    
    public void calMemberShip(double[][] newCenter){  
        memberShip = new int[DATA.length];  
        elementsInCenters = new int[k];  
        for(int j=0;j<DATA.length;j++){  
            double currentDistance = Double.MAX_VALUE;  
            int currentIndex = -1;  
            double[] item = DATA[j];  
            for(int i=0;i<k;i++){  
                //中心点  
                double[] tempCentersValue = newCenter[i];  
                double distance = this.manhattanDistince(item, tempCentersValue);  
                if(distance<currentDistance){  
                    currentDistance = distance;  
                    currentIndex = i;  
                }  
            }  
            memberShip[j] = currentIndex;  
        }  
          
        for(int i=0;i<memberShip.length;i++){  
            elementsInCenters[memberShip[i]]++;  
        }  
    } 
      
    
    /*
     * 
     */
    public int calMemberShip(double[] target,double[][] latesCenter){
    	int currentIndex=-1;
    	double currentDistance = Double.MAX_VALUE;  
    	for(int i=0;i<latesCenter.length;i++){
    		double result=this.manhattanDistince(target, latesCenter[i]);
    		if(result<currentDistance){  
                currentDistance = result;  
                currentIndex = i;  
            } 
    	}
    	return currentIndex;
    }
    
    /*
     * 每次聚类后新的类中心点
     */
    public void calNewCenters(){  
        centers = new double[k][DATA[0].length];  
        for(int i=0;i<memberShip.length;i++){  
            for(int j=0;j<DATA[0].length;j++){  
                centers[memberShip[i]][j] += DATA[i][j];  
            }  
        }  
          
        for(int i=0;i<centers.length;i++){  
            for(int j=0;j<DATA[0].length;j++){  
                centers[i][j] /= elementsInCenters[i];  
            }  
        }  
    }  
    
  
    public double computeTotalDistance() {  
        double tempTotal = 0;  
        for (int i = 0; i < DATA.length; i ++) {  
            tempTotal += manhattanDistince(DATA[i], centers[memberShip[i]]);  
        }  
        return tempTotal;  
    }  
}  
