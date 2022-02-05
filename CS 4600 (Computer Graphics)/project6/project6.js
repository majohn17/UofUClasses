var raytraceFS = `
struct Ray {
	vec3 pos;
	vec3 dir;
};

struct Material {
	vec3  k_d;	// diffuse coefficient
	vec3  k_s;	// specular coefficient
	float n;	// specular exponent
};

struct Sphere {
	vec3     center;
	float    radius;
	Material mtl;
};

struct Light {
	vec3 position;
	vec3 intensity;
};

struct HitInfo {
	float    t;
	vec3     position;
	vec3     normal;
	Material mtl;
};

uniform Sphere spheres[ NUM_SPHERES ];
uniform Light  lights [ NUM_LIGHTS  ];
uniform samplerCube envMap;
uniform int bounceLimit;

bool IntersectRay( inout HitInfo hit, Ray ray );

// Shades the given point and returns the computed color.
vec3 Shade( Material mtl, vec3 position, vec3 normal, vec3 view )
{
	vec3 color = vec3(0,0,0);
	for ( int i=0; i < NUM_LIGHTS; ++i ) {
		Ray ray;
		
		ray.pos = position;
		ray.dir = normalize(lights[i].position - position);

		HitInfo hit;

		if (!IntersectRay(hit, ray)) {
			vec3 diffuseColor = mtl.k_d;
			vec3 specularColor = mtl.k_s;

			vec3 viewDirection = view;
			vec3 lightDirection = ray.dir;
			vec3 h = normalize(lightDirection + viewDirection);

			float cosTheta = max(0.0, dot(normal, lightDirection));
			float cosPhi = max(0.0, dot(normal, h));
			float specPower = pow(cosPhi, mtl.n);

			vec3 result = lights[i].intensity * (cosTheta * diffuseColor + specularColor * specPower);
			color += result;
		}
	}
	return color;
}

// Intersects the given ray with all spheres in the scene
// and updates the given HitInfo using the information of the sphere
// that first intersects with the ray.
// Returns true if an intersection is found.
bool IntersectRay( inout HitInfo hit, Ray ray )
{
	hit.t = 1e30;
	bool foundHit = false;

	for ( int i=0; i<NUM_SPHERES; ++i ) {
		vec3 pc = ray.pos - spheres[i].center;
		float a = dot(ray.dir, ray.dir);
		float b = 2.0 * dot(ray.dir, pc);
		float c = dot(pc, pc) - pow(spheres[i].radius, 2.0);
		float delta = (b*b) - (4.0*a*c);

		if (delta >= 0.0) {
			float t = (-b - sqrt(delta)) / (2.0*a);
			vec3 hitPos = ray.pos + ray.dir*t;

			if (t >= 1e-5 && t <= hit.t) {
				foundHit = true;
				hit.t = t;
				hit.position = hitPos;
				hit.normal = normalize(hitPos - spheres[i].center);
				hit.mtl = spheres[i].mtl;
			}	
		}
	}

	return foundHit;
}

// Given a ray, returns the shaded color where the ray intersects a sphere.
// If the ray does not hit a sphere, returns the environment color.
vec4 RayTracer( Ray ray )
{
	HitInfo hit;
	if ( IntersectRay( hit, ray ) ) {
		vec3 view = normalize( -ray.dir );
		vec3 clr = Shade( hit.mtl, hit.position, hit.normal, view );
		
		// Compute reflections
		vec3 k_s = hit.mtl.k_s;
		for ( int bounce=0; bounce<MAX_BOUNCES; ++bounce ) {
			if ( bounce >= bounceLimit ) break;
			if ( hit.mtl.k_s.r + hit.mtl.k_s.g + hit.mtl.k_s.b <= 0.0 ) break;
			
			Ray r;	// this is the reflection ray
			HitInfo h;	// reflection hit info
			
			r.pos = hit.position;
			vec3 viewDirection = normalize(ray.pos - hit.position);
			r.dir = 2.0*dot(viewDirection, hit.normal)*hit.normal - viewDirection;
			
			if ( IntersectRay( h, r ) ) {
				clr += k_s * Shade(h.mtl, h.position, h.normal, normalize(-r.dir));
				k_s *= h.mtl.k_s;
				ray = r;
				hit = h;
			} 
			else {
				clr += k_s * textureCube( envMap, r.dir.xzy ).rgb;
				break;
			}
		}
		return vec4( clr, 1 );
	} else {
		return vec4( textureCube( envMap, ray.dir.xzy ).rgb, 1);
	}
}
`;