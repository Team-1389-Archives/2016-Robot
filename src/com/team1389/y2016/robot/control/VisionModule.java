package com.team1389.y2016.robot.control;


import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.control.SetASetpointCommand;
import com.team1389.y2016.robot.RobotLayout;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionModule {
	TurntableControl turntable;
	final int ImgWidth=170;
	final int FOV=60;
	Solenoid ringLight;
	public VisionModule(Solenoid ringLight){
	//	NetworkTable.setPort(8080);
		this.ringLight=ringLight;
	}
	public Command lightsOn(){
		return new SolenoidControlCommand(true,ringLight);
	}
	public Command lightsOff(){
		return new SolenoidControlCommand(false,ringLight);
	}
	public Command autoAim(RobotLayout layout,InputDevice joy){
		return new Command(){

			@Override
			public boolean execute() {
				lightsOn().execute();
				boolean inView=getCorrectionAngle()!=-1;
				SmartDashboard.putBoolean("Target in View", inView);
				if(joy.getButton(6).isTriggered()&&inView){
				CommandsUtil.combineSequential(
							new SetASetpointCommand(layout.subsystems.armSetpointProvider, .12),
							new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, .1, .3),
							layout.subsystems.turntable.moveAngleCommand(getCorrectionAngle()))		
				 .execute();
				}
				return false;
			}

		};
	}
	public double getCorrectionAngle(){
		NetworkTable grip=NetworkTable.getTable("GRIP");
		//int targetIndex=getLargestArea(grip.getNumberArray("targets/area", new double[2]));
		double[] areas=grip.getSubTable("myContoursReport").getNumberArray("area",new double[0]);
		if(areas.length>0){
			double centerX= (int)grip.getSubTable("myContoursReport").getNumberArray("centerX",new double[0])[getLargestArea(areas)];
			double pixelOffset=centerX-ImgWidth/2;
			double percentOffset=pixelOffset/ImgWidth;
			double angleOffset=percentOffset*FOV;
			return -angleOffset;
		}
		return -1;
		/*for(double area:areas){
			System.out.println(area);
		}*/
		/*System.out.println(grip.getNumberArray("targets/area", new double[0])[0]);
		int centerX = (int) grip.getNumberArray("targets/centerX", new double[0])[targetIndex];
		int pixelOffset=centerX-ImgWidth/2;
		System.out.println("off by "+pixelOffset+" pixels");
		double percentOffset=pixelOffset/ImgWidth;
		double angleOffset=percentOffset*FOV;
		System.out.println("off by "+angleOffset+"degrees");
		return angleOffset;*/
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
