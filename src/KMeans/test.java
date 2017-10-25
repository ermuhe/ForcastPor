package KMeans;

import java.text.ParseException;
import java.util.Arrays;

public class test {

	public static void main(String[] args) throws NumberFormatException, ParseException {  
        KMeans kmeans = new KMeans(5);  
        String lastMembership = "";  
        String nowMembership = "";  
        int i=1;  
        kmeans.randomCenters();  
        System.out.println("随机选中的中心index为："+Arrays.toString(kmeans.centersIndex));
        kmeans.calMemberShip(kmeans.centersIndex);  
        nowMembership = Arrays.toString(kmeans.memberShip);  
        System.out.println("DATA聚类之后Membership为："+nowMembership);  
        System.out.println("Elements in centers cnt:"+Arrays.toString(kmeans.elementsInCenters)); 
        do{  
            if(nowMembership.equals(lastMembership)){  
                System.out.println("本次聚类与上次相同，退出执行！");  
                System.out.println("一共聚类了 "+i+" 次！");  
                 
//                double[] b={ 6.9, 3.1, 5.1, 2.3 };
                System.out.println("新中心点为："+Arrays.deepToString(kmeans.centers)); 
//                int fenlei=kmeans.calMemberShip(b, kmeans.centers);
//                System.out.println("b的分类是： "+fenlei);
//                double totalDistance = kmeans.computeTotalDistance();  
//                System.out.println("totalDistance ： "+totalDistance);  
                break;  
            }else{  
            	i++; 
                lastMembership = nowMembership;
                kmeans.calNewCenters(); 
                kmeans.calMemberShip(kmeans.centers);
                nowMembership = Arrays.toString(kmeans.memberShip);
            }  
            System.out.println("----------------分割线----------------");  
        } while(true); 
    }  
}
