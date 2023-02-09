package com.marcs.test.factory.utility;

import org.springframework.context.annotation.ComponentScan;

/**
 * Defines the the base component scan packages for the DAO.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
@ComponentScan(basePackages = {"com.marcs.app.auth.dao"})
public class MarcsDAOTestConfig {}