/*
  @Virtual Machine Monitor
 */

import java.io.IOException;


public class Main {
	
	 public static void main(String args[])
	 {
		 	
		final MyVM myvm = new MyVM("VijayRaj2");
		myvm.setAlarm();
		myvm.helloVM();
		
		// First Thread for checking the status of the Virtual Machines
		Thread t1 = new Thread()
		{
			public void run()
			{
				while(true)
				{
					try
					{
						// checking if the host i alive or not
						
					// if the vm is responding
				boolean result = myvm.pingVM();
				// pinging is successful
				if (result == true) 
				System.out.println("virtual machine is alive");
				
				// pinging fails
				else 
				{
					String state =myvm.getVMState();
					// if the virtual machine is turned off
					if (state.equalsIgnoreCase("poweredoff")) 
					{
						System.out.println("VM is Powered off");
						System.out.println("Please wait for the user to power it on");
					} 
					else // the worst case
					{
						System.out.println("VM is powered on and is not responding");
						System.out.println(" The virtual machine failed");
						
						/*boolean hostresult = myvm.pingHost();
						if(hostresult == true)
						{
						System.out.println("Host is working ");
						System.out.println("The virtual machine will be cloned in the same host");
						try
						{
							myvm.cloneToSameHost("", "");
							Thread.sleep(10000);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						}
						else
						{
						 System.out.println("Host is not working");
						 System.out.println("we need to check the other hosts on the Data center");
						 try
						 {
							 myvm.checkHost();*/
							 boolean hresult = myvm.pingSecondHost();
								
								// if second is found
								if(hresult == true)
								{
								System.out.println("Another host found working on the same Vcenter");
								System.out.println("The Virtual machine would be cloned and migrated");
								System.out.println("This may take a few minutes. please wait.......");
								try
								{
									myvm.cloneFromSnapshot("","");
									Thread.sleep(10000);
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
								}
								else
								System.out.println("no alive host found");
						/* }
						 catch (Exception e)
						 {
							 e.printStackTrace();
						 } */
						}
						
					}
				}
		//	}
					catch (IOException e)
					{
						e.printStackTrace();
					}
			//pinging every 10 sec
				try
				{
					Thread.sleep(10000);
					
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	};
	
	//second Thread for Creating the SnapShots
	Thread t2 = new Thread()
	{
		public void run()
		{
			while(true)
			{
				myvm.snapShot("","create");
				System.out.println("SnapShot is created");
				try
				{
					Thread.sleep(10000); 
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	};
myvm.getAlarm();
t1.start();
t2.start();
	 }
}

