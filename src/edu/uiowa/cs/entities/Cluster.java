/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.entities;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author WakeupBlake
 */
public class Cluster {
    private SemanticDescriptorVector centroid;
    private List<ClusterMember> members;

    public Cluster() {
    }

    public Cluster(SemanticDescriptorVector centroid) {
        this.centroid = centroid;
        this.members = new ArrayList<>();
        
    }
    
    

    /**
     * @return the centroid
     */
    public SemanticDescriptorVector getCentroid() {
        return centroid;
    }

    /**
     * @param centroid the centroid to set
     */
    public void setCentroid(SemanticDescriptorVector centroid) {
        this.centroid = centroid;
    }

    /**
     * @return the members
     */
    public List<ClusterMember> getMembers() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(List<ClusterMember> members) {
        this.members = members;
    }
    
    public void addMember(ClusterMember member){
        this.members.add(member);
    }

    @Override
    public String toString() {
        
        String ret = "";
        
        for(ClusterMember member:this.getMembers()){
                ret += member.getSdv().getWord()+",";
        }
        
        return ret;
                
    }
}

    
    
