#version 330 core
in vec2 position;
in vec3 color;
in vec2 texCoords;

uniform float windowStretch;

out vec2 fTexCoords;
out vec3 fColor;

void main()
{
 fColor = color;
 fTexCoords = texCoords;

 vec2 newPos = vec2(position.x*windowStretch, position.y);
 gl_Position = vec4(newPos, 0.0, 1.0);
}