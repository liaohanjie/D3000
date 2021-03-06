package org.d3.module;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.d3.D3Context;
import org.d3.net.packet.InPacket;
import org.d3.net.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public abstract class BaseModule implements Module {
	
	private static Logger LOG = LoggerFactory.getLogger(BaseModule.class);
	
	protected Map<Integer, Processor> processors = Maps.newHashMap();
	
	@PostConstruct
	public void registerModule() {
		Registry registry = (Registry) D3Context.getBean("dispatcher");
		if(registry != null){
			if(LOG.isDebugEnabled()){
				LOG.debug(getDescription());
			}
			registry.register(getType(), this);
		}
	}

	public void service(Session session, InPacket pkt) {
		int cmd = pkt.getCmd();
		Processor processor = getProcessor(cmd);
		if(processor != null){
			processor.process(session, pkt);
		}
	}
	
	public Processor getProcessor(int cmd){
		return processors.get(cmd);
	}
	
	public void register(int type, Processor processor) {
		processors.put(type, processor);
	}

}
