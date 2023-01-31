package com.cryptshare.planningpoker.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.StringJoiner;

/**
 * Data of an extension.
 */
@Entity
@Table(name = "extension")
public class Extension extends BaseEntity {

	@Column(name = "extension_key", nullable = false)
	private String key;

	@Column(name = "extension_enabled", nullable = false)
	private boolean enabled = true;

	protected Extension() {
	}

	public Extension(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String extension) {
		this.key = extension;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Extension.class.getSimpleName() + "[", "]").add("key='" + key + "'").add("enabled=" + enabled).toString();
	}
}