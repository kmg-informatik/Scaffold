#version 330 core
in vec2 position;
in vec3 color;
in vec2 texCoords;

out vec2 fTexCoords;
out vec3 fColor;

void main()
{
 fColor = color;
 fTexCoords = texCoords;
 gl_Position = vec4(position, 0.0, 1.0);
}