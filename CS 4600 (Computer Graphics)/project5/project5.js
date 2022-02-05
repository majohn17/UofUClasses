// This function takes the translation and two rotation angles (in radians) as input arguments.
// The two rotations are applied around x and y axes.
// It returns the combined 4x4 transformation matrix as an array in column-major order.
// You can use the MatrixMult function defined in project5.html to multiply two 4x4 matrices in the same format.
function GetModelViewMatrix( translationX, translationY, translationZ, rotationX, rotationY )
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
	return tr;
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
		this.mv = gl.getUniformLocation(this.prog, 'mv');
		this.normMat = gl.getUniformLocation(this.prog, 'normMat');
		this.sampler = gl.getUniformLocation(this.prog, 'tex');
		this.swap = gl.getUniformLocation(this.prog, 'swap');
		this.show = gl.getUniformLocation(this.prog, 'show');
		this.lightDir = gl.getUniformLocation(this.prog, 'lightDir');
		this.shine = gl.getUniformLocation(this.prog, 'shine');

		// Get the ids of the vertex attributes in the shaders
		this.pos = gl.getAttribLocation(this.prog, 'pos');
		this.txc = gl.getAttribLocation(this.prog, 'txc');
		this.norm = gl.getAttribLocation(this.prog, 'norm');

		// Create buffers for the vertex attribute data
		this.posBuffer = gl.createBuffer();
		this.texBuffer = gl.createBuffer();
		this.normBuffer = gl.createBuffer();

		// Deal with first texture box click
		this.firstShow = false;
	}
	
	// This method is called every time the user opens an OBJ file.
	// The arguments of this function is an array of 3D vertex positions,
	// an array of 2D texture coordinates, and an array of vertex normals.
	// Every item in these arrays is a floating point value, representing one
	// coordinate of the vertex position or texture coordinate.
	// Every three consecutive elements in the vertPos array forms one vertex
	// position and every three consecutive vertex positions form a triangle.
	// Similarly, every two consecutive elements in the texCoords array
	// form the texture coordinate of a vertex and every three consecutive 
	// elements in the normals array form a vertex normal.
	// Note that this method can be called multiple times.
	setMesh( vertPos, texCoords, normals )
	{
		// Bind the vertex position, tex data, and normals to their buffers created in the constructor
		gl.bindBuffer(gl.ARRAY_BUFFER, this.posBuffer);
		gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertPos), gl.STATIC_DRAW);
		gl.bindBuffer(gl.ARRAY_BUFFER, this.texBuffer);
		gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(texCoords), gl.STATIC_DRAW);
		gl.bindBuffer(gl.ARRAY_BUFFER, this.normBuffer);
		gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normals), gl.STATIC_DRAW);
		
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
	// The arguments are the model-view-projection transformation matrixMVP,
	// the model-view transformation matrixMV, the same matrix returned
	// by the GetModelViewProjection function above, and the normal
	// transformation matrix, which is the inverse-transpose of matrixMV.
	draw( matrixMVP, matrixMV, matrixNormal )
	{
		gl.useProgram(this.prog);

		gl.uniformMatrix4fv(this.mvp, false, matrixMVP);
		gl.uniformMatrix4fv(this.mv, false, matrixMV);
		gl.uniformMatrix3fv(this.normMat, false, matrixNormal);

		gl.bindBuffer(gl.ARRAY_BUFFER, this.posBuffer);
		gl.vertexAttribPointer(this.pos, 3, gl.FLOAT, false, 0, 0);
		gl.enableVertexAttribArray( this.pos);
		gl.bindBuffer(gl.ARRAY_BUFFER, this.texBuffer);
		gl.vertexAttribPointer(this.txc, 2, gl.FLOAT, false, 0, 0);
		gl.enableVertexAttribArray(this.txc);
		gl.bindBuffer(gl.ARRAY_BUFFER, this.normBuffer);
		gl.vertexAttribPointer(this.norm, 3, gl.FLOAT, false, 0, 0);
		gl.enableVertexAttribArray(this.norm);

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
	
	// This method is called to set the incoming light direction
	setLightDir( x, y, z )
	{
		gl.useProgram(this.prog);
		gl.uniform3f(this.lightDir, x, y, z);
	}
	
	// This method is called to set the shininess of the material
	setShininess( shininess )
	{
		gl.useProgram(this.prog);
		gl.uniform1f(this.shine, shininess);
	}
}

// Vertex Shader
var meshVS = `
	attribute vec3 pos;
	attribute vec2 txc;
	attribute vec3 norm;

	uniform mat4 mvp;
	uniform mat4 mv;
	uniform mat3 normMat;
	uniform int swap;

	varying vec2 texCoord;
	varying vec3 viewPos;
	varying vec3 viewNorm;

	void main()
	{
		if (swap == 1) {
			texCoord = txc;
			vec4 vertPos4 = mv * vec4(pos[0], pos[2], pos[1], 1.0);
			viewPos = vec3(vertPos4);
			viewNorm = normMat * norm;
			gl_Position = mvp * vec4(pos[0], pos[2], pos[1], 1.0);
		}
		else {
			texCoord = txc;
			vec4 vertPos4 = mv * vec4(pos, 1.0);
			viewPos = vec3(vertPos4);
			viewNorm = normMat * norm;
			gl_Position = mvp * vec4(pos, 1.0);
		}
	}
`;

// Fragment Shader
var meshFS = `
	precision mediump float;

	uniform int show;
	uniform sampler2D tex;
	uniform vec3 lightDir;
	uniform float shine;

	varying vec2 texCoord;
	varying vec3 viewPos;
	varying vec3 viewNorm;
	
	void main()
	{
		vec3 diffuseColor = vec3(1.0, 1.0, 1.0);
		if (show == 1) {
			diffuseColor = vec3(texture2D(tex, texCoord));
		}

		vec3 lightColor = vec3(1.0, 1.0, 1.0);
		vec3 specularColor = vec3(1.0, 1.0, 1.0);

		vec3 viewDirection = normalize(-viewPos);
		vec3 lightDirection = lightDir;
		vec3 h = normalize(lightDirection + viewDirection);
		vec3 normal = viewNorm;

		float cosTheta = dot(normal, lightDir);
		float cosPhi = dot(normal, h);
		float specPower = pow(cosPhi, shine);

		vec3 result = lightColor * (cosTheta * diffuseColor + specularColor * specPower);
		gl_FragColor = vec4(result, 1.0);
	}
`;
