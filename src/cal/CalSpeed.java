package cal;

import java.util.LinkedList;
import java.util.ListIterator;

import model.SyslogsBean;

public class CalSpeed {
	public double getSpeed(LinkedList<SyslogsBean> list1,LinkedList<SyslogsBean> list2){
		//list1 newlist,list2 oldlist;
		if(list1.size() != list2.size()) {
			System.out.println("log num not equal!!!");
		}
		int listSize = list1.size();
		int myId1 = getCurrentMyId(list1); //currentMyId1
		int myId2 = getCurrentMyId(list2);//currentMyId2
		long sub = -1L;
		
		if(myId1 == myId2) {
			SyslogsBean bean1 = getSyslogsBeanByMyId(list1,myId1);
			SyslogsBean bean2 = getSyslogsBeanByMyId(list2,myId2);
			sub = bean1.getUsed() - bean2.getUsed();
		}
		

		if(myId1 < myId2 ) {
			if( myId2 -myId1 < listSize/10) {
				//error old > new 
				
			} else{ // loop max queen size
				for(int i = 1; i <= myId1; i++) {
					sub = sub + getSyslogsBeanByMyId(list1,i).getUsed();
				}
				for(int i = myId2; i <= listSize; i++) {
					sub = sub +(getSyslogsBeanByMyId(list1,i).getUsed()-getSyslogsBeanByMyId(list2,i).getUsed());
				}
			}
		}else {
			if( myId1 -myId2 < listSize/10) {
				sub = getSyslogsBeanByMyId(list1,myId2).getUsed() - getSyslogsBeanByMyId(list2,myId2).getUsed();
				for(int i = myId2+1; i <= myId1; i++) {
					sub = sub + getSyslogsBeanByMyId(list1,i).getUsed();
				}
			} else{ // loop max queen size
				// error old > new 
			}
		}
		
		if(sub < 0) {
			System.out.println("error");
			return 0;
		} else 
		{
			double speed = sub /30.0;
			return speed;
			
		}
	}
	
	
	public int getCurrentMyId(LinkedList<SyslogsBean> list) {
		ListIterator<SyslogsBean> it = list.listIterator();
		while(it.hasNext()) {
			SyslogsBean item = it.next();
			if(item.getIsCurrent() == 1) {
				return item.getMyId();
			}
		}
		return -1;
	}
	
	public SyslogsBean getSyslogsBeanByMyId(LinkedList<SyslogsBean> list,int myId) {
		ListIterator<SyslogsBean> it = list.listIterator();
		while(it.hasNext()) {
			SyslogsBean item = it.next();
			if(item.getMyId() == myId) {
				return item;
			}
		}
		return null;
	}
		
}
