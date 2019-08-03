#!/bin/bash
clear
echo "Criando imagem..."

docker build -t araujo/sna:latest .

echo "Imagem criada!"

