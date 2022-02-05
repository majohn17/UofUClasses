// Returns a 3x3 transformation matrix as an array of 9 values in column-major order.
// The transformation first applies scale, then rotation, and finally translation.
// The given rotation value is in degrees.
function GetTransform( positionX, positionY, rotation, scale )
{
	var radians = rotation * (Math.PI / 180);
	var s = Array( scale, 0, 0, 0, scale, 0, 0, 0, 1 );
	var r = Array( Math.cos(radians), Math.sin(radians), 0, -Math.sin(radians), Math.cos(radians), 0, 0, 0, 1);
	var t = Array( 1, 0, 0, 0, 1, 0, positionX, positionY, 1);

	var rs = MatrixMultiply(r, s);
	return MatrixMultiply(rs, t);
}

// Returns a 3x3 transformation matrix as an array of 9 values in column-major order.
// The arguments are transformation matrices in the same format.
// The returned transformation first applies trans1 and then trans2.
function ApplyTransform( trans1, trans2 )
{
	return MatrixMultiply(trans1, trans2);
}

// Given 2 3x3 matrices as an arrays of 9 values in column-major order, returns the dot product of m1 x m2 in the same form.
function MatrixMultiply( m1, m2 )
{
	var ret = Array( 0, 0, 0, 0, 0, 0, 0, 0, 0 );
	ret[0] = (m1[0]*m2[0]) + (m1[1]*m2[3]) + (m1[2]*m2[6]);
	ret[1] = (m1[0]*m2[1]) + (m1[1]*m2[4]) + (m1[2]*m2[7]);
	ret[2] = (m1[0]*m2[2]) + (m1[1]*m2[5]) + (m1[2]*m2[8]);
	ret[3] = (m1[3]*m2[0]) + (m1[4]*m2[3]) + (m1[5]*m2[6]);
	ret[4] = (m1[3]*m2[1]) + (m1[4]*m2[4]) + (m1[5]*m2[7]);
	ret[5] = (m1[3]*m2[2]) + (m1[4]*m2[5]) + (m1[5]*m2[8]);
	ret[6] = (m1[6]*m2[0]) + (m1[7]*m2[3]) + (m1[8]*m2[6]);
	ret[7] = (m1[6]*m2[1]) + (m1[7]*m2[4]) + (m1[8]*m2[7]);
	ret[8] = (m1[6]*m2[2]) + (m1[7]*m2[5]) + (m1[8]*m2[8]);
	return ret;
}
