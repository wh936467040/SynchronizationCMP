package alarm;

/*
 * Singleton pattern
 */
public  class SychronAlarm {
		protected String nodeId;
		private final String itemId ="00020027";
		protected String data;
		protected String startTime;
		protected String endTime ="0";
		protected int mark  = 0;
		protected String status;
	    
		public SychronAlarm(){
			
		}
	    
		
	    public int setInstance(String nodeId,String data,String startTime,String status){
	    	this.nodeId = nodeId;
	    	this.data = data;
	    	this.startTime = startTime;
	    	this.status = status;
			return 0;
	    }

		public String getNodeId() {
			return nodeId;
		}

		public void setNodeId(String nodeId) {
			this.nodeId = nodeId;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public int getMark() {
			return mark;
		}
		
		public String getItemId() {
			return itemId;
		}

		public void setMark(int mark) {
			this.mark = mark;
			if(mark == 0) {
				this.data = " 0 ";
				this.startTime = "0";
				this.endTime = "0";
			}
		}


		public String getStatus() {
			return status;
		}


		public void setStatus(String status) {
			this.status = status;
		}  
			
}
