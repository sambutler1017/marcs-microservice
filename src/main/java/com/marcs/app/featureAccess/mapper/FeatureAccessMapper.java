package com.marcs.app.featureAccess.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.featureAccess.client.domain.Feature;

import org.springframework.jdbc.core.RowMapper;

/**
 * Mapper class to map a Feature Object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class FeatureAccessMapper implements RowMapper<Feature> {
	public static FeatureAccessMapper FEATURE_ACCESS_MAPPER = new FeatureAccessMapper();

	public Feature mapRow(ResultSet rs, int rowNum) throws SQLException {
		Feature feature = new Feature();
		feature.setApp(rs.getString("feature_application_text"));
		feature.setFeature(rs.getString("feature_name_text"));
		feature.setAccess(rs.getString("access"));
		return feature;
	}
}
