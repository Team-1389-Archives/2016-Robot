package com.team1389.y2016.robot.test;

import org.strongback.command.Command;

import com.team1389.base.util.Timer;
import com.team1389.base.webserver.chart.Chart;
import com.team1389.base.webserver.chart.DataPoint;
import com.team1389.base.webserver.chart.WebChartManager;

import edu.wpi.first.wpilibj.CANTalon;

public class GraphVoltageCommand extends Command{
	CANTalon talon;
	Chart chart;
	Timer timer;
	
	public GraphVoltageCommand(CANTalon talon) {
		this.talon = talon;
		chart = new Chart(1, 1, "time", "voltage");
		WebChartManager.getInstance().addChart("voltage", chart);
		timer = new Timer();
	}

	@Override
	public void initialize() {
		timer.zero();
	}

	@Override
	public boolean execute() {
		chart.addPoint(new DataPoint(timer.get(), talon.getBusVoltage()));
		return false;
	}

}
