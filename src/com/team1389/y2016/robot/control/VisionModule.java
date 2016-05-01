package com.team1389.y2016.robot.control;


import java.util.concurrent.SynchronousQueue;

import org.strongback.command.Command;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionModule {
	final int ImgWidth=520;
	final int FOV=80;
    private final NetworkTable grip = NetworkTable.getTable("grip");
	Solenoid ringLight;
	public VisionModule(Solenoid ringLight){
		this.ringLight=ringLight;
	}
	public Command lightsOn(){
		return new SolenoidControlCommand(true,ringLight);
	}
	public Command lightsOff(){
		return new SolenoidControlCommand(false,ringLight);
	}
	public double getCorrectionAngle(){
		System.out.println(NetworkTable.getTable("grip").getSubTables());
		//int targetIndex=getLargestArea(grip.getNumberArray("targets/area", new double[2]));
		double[] areas=grip.getSubTable("targets").getNumberArray("area",new double[0]);
		System.out.println("hai");
		for(double area:areas){
			System.out.println(area);
		}
		/*System.out.println(grip.getNumberArray("targets/area", new double[0])[0]);
		int centerX = (int) grip.getNumberArray("targets/centerX", new double[0])[targetIndex];
		int pixelOffset=centerX-ImgWidth/2;
		System.out.println("off by "+pixelOffset+" pixels");
		double percentOffset=pixelOffset/ImgWidth;
		double angleOffset=percentOffset*FOV;
		System.out.println("off by "+angleOffset+"degrees");
		return angleOffset;*/
		return 0;
	}
	public int getLargestArea(double[] area){
		System.out.println(area);
		double largest=0;
		int index=-1;
		for(int x=0;x<area.length;x++){
			if(area[x]>largest){
				largest=area[x];
				index=x;
			}
		}
		return index;
	}
	
}
