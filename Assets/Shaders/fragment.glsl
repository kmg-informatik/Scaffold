#version 330 core

uniform sampler2D TEX_SAMPLER;

in vec3 fColor;
in vec2 fTexCoords;
out vec4 outColor;

void main() {
    vec4 texColor = texture(TEX_SAMPLER, fTexCoords);
    if(texColor.a <= 0) discard;
    outColor = texColor;
}