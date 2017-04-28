/**
 * @author XU Jiale
 */

package hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash {
    private SortedMap<Long, VirtualNode> circle = new TreeMap<Long,VirtualNode>();
    private MD5Hash hashfunction = new MD5Hash();

    public ConsistentHash(Collection<PhysicalNode> pNodes, int vNodeCount) {
        for (PhysicalNode pNode : pNodes) {
            addNode(pNode, vNodeCount);
        }
    }

    public void addNode(PhysicalNode pNode, int vNodeCount){
        int existingReplicas = getReplicas(pNode.toString());
        for(int i = 0; i < vNodeCount; i++) {
            VirtualNode vNode = new VirtualNode(pNode,i+existingReplicas);
            circle.put(hashfunction.hash(vNode.toString()), vNode);
        }
    }

    public void removeNode(PhysicalNode pNode) {
        Iterator<Long> it = circle.keySet().iterator();
        while (it.hasNext()) {
            Long key = it.next();
            VirtualNode virtualNode = circle.get(key);
            if (virtualNode.matches(pNode.toString())) {
                it.remove();
            }
        }
    }

    public VirtualNode getNode(String key){
        if (circle.isEmpty()) {
            return null;
        }
        Long hashKey = hashfunction.hash(key);
        SortedMap<Long, VirtualNode> tailMap = circle.tailMap(hashKey);
        hashKey = tailMap != null && !tailMap.isEmpty() ? tailMap.firstKey() : circle.firstKey();
        return circle.get(hashKey);
    }

    private int getReplicas(String nodeName) {
        int replicas = 0;
        for (VirtualNode node : circle.values()) {
            if (node.matches(nodeName)) {
                ++replicas;
            }
        }
        return replicas;
    }

    private static class MD5Hash {
        MessageDigest instance;

        public MD5Hash() {
            try {
                instance = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        long hash(String key) {
            instance.reset();
            instance.update(key.getBytes());
            byte[] digest = instance.digest();

            long h = 0;
            for (int i = 0; i < 4; i++) {
                h <<= 8;
                h |= digest[i] & 0xFF;
            }
            return h;
        }
    }
}
