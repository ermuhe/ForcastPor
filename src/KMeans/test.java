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
        System.out.println("���ѡ�е�����indexΪ��"+Arrays.toString(kmeans.centersIndex));
        kmeans.calMemberShip(kmeans.centersIndex);  
        nowMembership = Arrays.toString(kmeans.memberShip);  
        System.out.println("DATA����֮��MembershipΪ��"+nowMembership);  
        System.out.println("Elements in centers cnt:"+Arrays.toString(kmeans.elementsInCenters)); 
        do{  
            if(nowMembership.equals(lastMembership)){  
                System.out.println("���ξ������ϴ���ͬ���˳�ִ�У�");  
                System.out.println("һ�������� "+i+" �Σ�");  
                 
//                double[] b={ 6.9, 3.1, 5.1, 2.3 };
                System.out.println("�����ĵ�Ϊ��"+Arrays.deepToString(kmeans.centers)); 
//                int fenlei=kmeans.calMemberShip(b, kmeans.centers);
//                System.out.println("b�ķ����ǣ� "+fenlei);
//                double totalDistance = kmeans.computeTotalDistance();  
//                System.out.println("totalDistance �� "+totalDistance);  
                break;  
            }else{  
            	i++; 
                lastMembership = nowMembership;
                kmeans.calNewCenters(); 
                kmeans.calMemberShip(kmeans.centers);
                nowMembership = Arrays.toString(kmeans.memberShip);
            }  
            System.out.println("----------------�ָ���----------------");  
        } while(true); 
    }  
}
