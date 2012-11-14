/*
 * CubeFaceComparator.java
 *
 * A Comparator for putting the cube faces in the correct Z-order as it rotates.
 *
 * Developed by James L. Weaver (jim.weaver#javafxpert.com) to demonstrate the
 * use of 3D features in the JavaFX 2.0 API
 */
package javafxpert.cube;

import java.util.Comparator;

/**
 * 
 * @author Jim Weaver
 */
public class CubeFaceComparator implements Comparator<Object> {
	public int compare(Object cubeFaceB, Object cubeFaceA) {
		CubeFace faceA = (CubeFace) cubeFaceA;
		CubeFace faceB = (CubeFace) cubeFaceB;
		return ((CubeFace) faceA).zPos.getValue().compareTo(
				((CubeFace) faceB).zPos.getValue());
	}
}
