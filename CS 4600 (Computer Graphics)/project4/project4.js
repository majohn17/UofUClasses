// This function takes the projection matrix, the translation, and two rotation angles (in radians) as input arguments.
// The two rotations are applied around x and y axes.
// It returns the combined 4x4 transformation matrix as an array in column-major order.
// The given projection matrix is also a 4x4 matrix stored as an array in column-major order.
// You can use the MatrixMult function defined in project4.html to multiply two 4x4 matrices in the same format.
function GetModelViewProjection( projectionMatrix, translationX, translationY, translationZ, rotationX, rotationY )
{
	var rX = [ 
		1, 0, 0, 0, 
		0, Math.cos(rotationX), Math.sin(rotationX), 0, 
		0, -Math.sin(rotationX), Math.cos(rotationX), 0, 
		0, 0, 0, 1
	];
	var rY = [ 
		Math.cos(rotationY), 0, -Math.sin(rotationY), 0, 
		0, 1, 0, 0, 
		Math.sin(rotationY), 0, Math.cos(rotationY), 0, 
		0, 0, 0, 1
	];
	var trans = [
		1, 0, 0, 0,
		0, 1, 0, 0,
		0, 0, 1, 0,
		translationX, translationY, translationZ, 1
	];

	var rot = MatrixMult(rX, rY);
	var tr = MatrixMult(trans, rot);
	var mvp = MatrixMult(projectionMatrix, tr);
	return mvp;
}


// [TO-DO] Complete the implementation of the following class.

class MeshDrawer
{
	// The constructor is a good place for taking care of the necessary initializations.
	constructor()
	{
		// Compile the shader program
		this.prog = InitShaderProgram(meshVS, meshFS);
		
		// Get the ids of the uniform variables in the shaders
		this.mvp = gl.getUniformLocation(this.prog, 'mvp');
		this.sampler = gl.getUniformLocation(this.prog, 'tex');
		this.swap = gl.getUniformLocation(this.prog, 'swap');
		this.show = gl.getUniformLocation(this.prog, 'show');

		// Get the ids of the vertex attributes in the shaders
		this.pos = gl.getAttribLocation(this.prog, 'pos');
		this.txc = gl.getAttribLocation(this.prog, 'txc');

		// Create buffers for the vertex attribute data
		this.posBuffer = gl.createBuffer();
		this.texBuffer = gl.createBuffer();

		// Deal with first texture box click
		this.firstShow = false;
	}
	
	// This method is called every time the user opens an OBJ file.
	// The arguments of this function is an array of 3D vertex positions
	// and an array of 2D texture coordinates.
	// Every item in these arrays is a floating point value, representing one
	// coordinate of the vertex position or texture coordinate.
	// Every three consecutive elements in the vertPos array forms one vertex
	// position and every three consecutive vertex positions form a triangle.
	// Similarly, every two consecutive elements in the texCoords array
	// form the texture coordinate of a vertex.
	// Note that this method can be called multiple times.
	setMesh( vertPos, texCoords )
	{
		// Bind the vertex position and tex data to their buffers created in the constructor
		gl.bindBuffer(gl.ARRAY_BUFFER, this.posBuffer);
		gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertPos), gl.STATIC_DRAW);
		gl.bindBuffer(gl.ARRAY_BUFFER, this.texBuffer);
		gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(texCoords), gl.STATIC_DRAW);

		this.numTriangles = vertPos.length / 3;
	}
	
	// This method is called when the user changes the state of the
	// "Swap Y-Z Axes" checkbox. 
	// The argument is a boolean that indicates if the checkbox is checked.
	swapYZ( swap )
	{
		gl.useProgram(this.prog);
		if (swap) {
			gl.uniform1i(this.swap, 1);
		}
		else {
			gl.uniform1i(this.swap, 0);
		}
	}
	
	// This method is called to draw the triangular mesh.
	// The argument is the transformation matrix, the same matrix returned
	// by the GetModelViewProjection function above.
	draw( trans )
	{
		gl.useProgram(this.prog);

		gl.uniformMatrix4fv(this.mvp, false, trans);

		gl.bindBuffer(gl.ARRAY_BUFFER, this.posBuffer);
		gl.vertexAttribPointer(this.pos, 3, gl.FLOAT, false, 0, 0);
		gl.enableVertexAttribArray( this.pos);
		gl.bindBuffer(gl.ARRAY_BUFFER, this.texBuffer);
		gl.vertexAttribPointer(this.txc, 2, gl.FLOAT, false, 0, 0);
		gl.enableVertexAttribArray(this.txc);

		gl.drawArrays(gl.TRIANGLES, 0, this.numTriangles);
	}
	
	// This method is called to set the texture of the mesh.
	// The argument is an HTML IMG element containing the texture data.
	setTexture( img )
	{
		const mytex = gl.createTexture();
		gl.bindTexture(gl.TEXTURE_2D, mytex)

		gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGB, gl.RGB, gl.UNSIGNED_BYTE, img);
		gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR_MIPMAP_LINEAR);
		gl.generateMipmap(gl.TEXTURE_2D);
		
		gl.activeTexture(gl.TEXTURE0);
		gl.bindTexture(gl.TEXTURE_2D, mytex);

		gl.useProgram(this.prog);
		gl.uniform1i(this.sampler, 0);
		
		if (!this.firstShow) {
			gl.uniform1i(this.show, 1);
		}
	}
	
	// This method is called when the user changes the state of the
	// "Show Texture" checkbox. 
	// The argument is a boolean that indicates if the checkbox is checked.
	showTexture( show )
	{
		if (!this.firstShow) {
			this.firstShow = true;
		}

		gl.useProgram(this.prog);
		if (show) {
			gl.uniform1i(this.show, 1);
		}
		else {
			gl.uniform1i(this.show, 0);
		}
	}
}

// Vertex Shader
var meshVS = `
	attribute vec3 pos;
	attribute vec2 txc;
	uniform mat4 mvp;
	uniform int swap;
	varying vec2 texCoord;
	void main()
	{
		if (swap == 1) {
			gl_Position = mvp * vec4(pos[0], pos[2], pos[1], 1);
			texCoord = txc;
		}
		else {
			gl_Position = mvp * vec4(pos, 1);
			texCoord = txc;
		}
	}
`;

// Fragment Shader
var meshFS = `
	precision mediump float;
	uniform int show;
	uniform sampler2D tex;
	varying vec2 texCoord;
	void main()
	{
		if (show == 1) {
			gl_FragColor = texture2D(tex, texCoord);
		}
		else {
			gl_FragColor = vec4(1, gl_FragCoord.z*gl_FragCoord.z, 0, 1);
		}
	}
`;
