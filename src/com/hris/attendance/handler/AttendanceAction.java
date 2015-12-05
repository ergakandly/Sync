package com.hris.attendance.handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.quartz.JobExecutionContext;

import com.hris.attendance.form.AttendanceForm;
import com.hris.attendance.manager.AttendanceManager;
import com.hris.attendance.plugin.QuartzPlugin;
import com.hris.attendance.quartz.SchedulerJob;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class AttendanceAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		QuartzPlugin qPlug = new QuartzPlugin();

		qPlug.init(null, null);
		return mapping.findForward("success");
	}

}
