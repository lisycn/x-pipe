package com.ctrip.xpipe.redis.meta.server.cluster;

import com.ctrip.xpipe.api.codec.Codec;
import com.ctrip.xpipe.utils.ObjectUtils;

/**
 * @author wenchao.meng
 *
 * Jul 23, 2016
 */
public class ClusterServerInfo {
	
	private String ip;
	private int port;

	public ClusterServerInfo(){
		
	}

	public ClusterServerInfo(String ip, int port){
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null){
			return false;
		}
		
		ClusterServerInfo other = (ClusterServerInfo) obj;
		return ObjectUtils.equals(ip, other.ip) && ObjectUtils.equals(port, other.port);
	}
	
   @Override
   public int hashCode() {
	   
      int hash = 0;

      hash = hash * 31 + (ip == null ? 0 : ip.hashCode());
      hash = hash * 31 + (port);
      return hash;
   }
	   
	@Override
	public String toString() {
		return Codec.DEFAULT.encode(this);
	}
}
