package org.apache.eventmesh.runtime.configuration;

import org.apache.eventmesh.common.config.CommonConfiguration;
import org.apache.eventmesh.common.config.ConfigurationWrapper;

public class EventMeshAmqpConfiguration extends CommonConfiguration {

    public int eventMeshAmqpServerPort = 10000;

    // default frame size of broker
    public int defaultNetworkBufferSize = 256 * 1024;

    /**
     * default min size of frame
     */
    public int minFrameSize = 4096;

    /**
     * default max size of frame
     */
    public int maxFrameSize = 4096;

    public EventMeshAmqpConfiguration(ConfigurationWrapper configurationWrapper) {
        super(configurationWrapper);
    }
}
