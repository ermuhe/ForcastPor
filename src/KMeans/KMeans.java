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
 * k-Means�㷨�������㷨��  
 * ʵ�ֲ��裺
 * 1. �����������ȡ�����е�K��Ԫ����Ϊ�����K�����ģ�  
 * 2. �������������е�Ԫ�ؽ��з��࣬ÿ��Ԫ�ض�ȥ�ж��Լ���K�����ĵľ��룬�����ൽ�����������ȥ��  
 * 3. ����ÿ�������ƽ��ֵ������Ϊ�µ����ĵ�  
 * 4. �ظ�2��3���裬ֱ����k�����ĵ㲻�ٱ仯�������ˣ�����ִ�����㹻��ĵ���  
 */  
public class KMeans {  
	
    private  double[][] DATA ;
    public int k;                 //k�����ĵ�  
    public int[] memberShip;      //ƥ���P�S
    public double[][] centersIndex;  
    public double[][] centers;    //����ֵ
    public int[] elementsInCenters;  //���ÿ�Ԫ��
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
     * ��������֮��Ĺ����پ��룬Ҳ�ɲ���ŷ����þ��� 
     */
    public double manhattanDistince(double []paraFirstData,double []paraSecondData){  
        double tempDistince = 0;  
        if((paraFirstData!=null && paraSecondData!=null) && paraFirstData.length==paraSecondData.length){  
            for(int i=0;i<paraFirstData.length;i++){  
                tempDistince += Math.abs(paraFirstData[i] - paraSecondData[i]);  
            }  
        }else{  
            System.out.println("firstData �� secondData ���ݽṹ��һ��");  
        }  
        return tempDistince;  
    }  
     
    /*
     * ���ȡK����
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
                //�����ĵ���±�浽MAP�У���֤�´�ѡ�������ĵ㲻��ͬһ��  
                map.put(index, DATA[index]);  
                //�����ĵ���±����centers[]��  
                centersIndex[i] = DATA[index];  
            }  
        }  
    }  
      
    /*
     * ƥ��ÿ�㵽K�����ӳ��
     */
//    public void calMemberShip(){  
//        memberShip = new int[DATA.length];  
//        elementsInCenters = new int[k];  
//        for(int j=0;j<DATA.length;j++){  
//            double currentDistance = Double.MAX_VALUE;  
//            int currentIndex = -1;  
//            double[] item = DATA[j];  
//            for(int i=0;i<k;i++){  
//                //���ĵ�  
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
                //���ĵ�  
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
     * ÿ�ξ�����µ������ĵ�
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
