#version 330 core
in vec2 position;
in vec3 color;
in vec2 texCoords;
in float texId;

uniform float windowStretch;
uniform mat4 cameraProjection;
uniform mat4 cameraView;
uniform float screenAlpha;

out vec2 fTexCoords;
out vec3 fColor;
out float fTexId;
out float fScreenAlpha;


void main()
{
    fColor = color;
    fTexCoords = texCoords;
    fTexId = texId;
    fScreenAlpha = screenAlpha;

    vec2 newPos = vec2(position.x*windowStretch, position.y);
    gl_Position = cameraProjection * cameraView * vec4(newPos, 0.0, 1.0);
}