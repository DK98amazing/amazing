/**
 * HostPortDto.
 *
 * @author liguoyao
 */
public class HostPortDto {
    private String hostIp;
    private String port;

    public HostPortDto(String hostIp, String port) {
        this.hostIp = hostIp;
        this.port = port;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HostPortDto dto = (HostPortDto) obj;
        if (!hostIp.equals(dto.getHostIp())) {
            return false;
        }
        if (!port.equals(dto.getPort())) {
            return false;
        }
        return true;
    }

    @Override public int hashCode() {
        int result = 17;
        result = result * 31 + hostIp.hashCode();
        result = result * 31 + port.hashCode();
        return result;
    }

    @Override public String toString() {
        return hostIp + ":" + port;
    }
}
