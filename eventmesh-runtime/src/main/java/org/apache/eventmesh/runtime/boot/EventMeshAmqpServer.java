package org.apache.eventmesh.runtime.boot;

import lombok.Data;
import org.apache.eventmesh.runtime.configuration.EventMeshAmqpConfiguration;

@Data
public class EventMeshAmqpServer extends AbstractRemotingServer{
    private EventMeshAmqpConfiguration eventMeshAmqpConfiguration;


}
