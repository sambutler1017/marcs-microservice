/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.app.featureaccess.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.featureaccess.client.domain.Feature;
import com.marcs.common.abstracts.AbstractMapper;

/**
 * Mapper class to map a Feature Object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class FeatureAccessMapper extends AbstractMapper<Feature> {
	public static FeatureAccessMapper FEATURE_ACCESS_MAPPER = new FeatureAccessMapper();

	public Feature mapRow(ResultSet rs, int rowNum) throws SQLException {
		Feature feature = new Feature();
		feature.setApp(rs.getString(FEATURE_APPLICATION_TEXT));
		feature.setFeature(rs.getString(FEATURE_NAME_TEXT));
		feature.setAccess(rs.getString(ACCESS));
		return feature;
	}
}
