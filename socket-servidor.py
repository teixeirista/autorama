#coding: utf-8
from threading import Thread
import socket
import json
import time
#from paho.mqtt import client as mqtt_client
import mercury
    

HOST = ''              # Endereco IP do Servidor
PORT = 5025            # Porta que o Servidor esta

def receber_mensagem(socket):
    tamanho_da_mensagem = int.from_bytes(socket.recv(2), byteorder='big')
    mensagem = socket.recv(tamanho_da_mensagem).decode("UTF-8")
    return mensagem

def enviar_mensagem(socket, mensagem):
    print(mensagem)
    socket.send(bytes(mensagem +"\r\n",'UTF-8'))

def config_leitor(aux):
    reader = mercury.Reader(aux[0], baudrate=int(aux[3]))
    reader.set_region(aux[1])
    reader.set_read_plan([1], aux[2], read_power=int(aux[4]))
    return reader

def run():
    global HOST, PORT

    servidor = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    orig = (HOST, PORT)
    servidor.bind(orig)
    servidor.listen(1)
    print ('Aguardando conexão...')

    conn, cliente = servidor.accept()
    print ('Concetado por', cliente)
        
    msg = receber_mensagem(conn)
    print(msg)
    aux = msg.split(';')

    leitor = config_leitor(aux)

    while True:
        msg = receber_mensagem(conn)
        msg_json = json.loads(msg)
        print(msg_json["comando"])
        print(msg_json["conteudo"])

        if "Ler tags" in msg_json["comando"]:
            epcs = map(lambda t: t.epc, leitor.read())
            aux = str(list(epcs))
            print(aux)
            enviar_mensagem(conn, aux)

        elif "Qualificação" in msg_json["comando"]:
            final = int(msg_json["conteudo"])

            aux_tags = receber_mensagem(conn)
            tags_json = json.loads(aux_tags)
            lista_tags = tags_json["conteudo"].split(';')

            tempo_aux = lista_tags.copy()

            for i in range(len(tempo_aux)):
                tempo_aux[i] = 0

            tempo_minimo = 5

            inicio = time.time()

            leitor.start_reading(lambda tag: leitura_tag(tag.epc, tag.timestamp))

            def leitura_tag(rfid, tempo_leitura):
                nonlocal tempo_aux, lista_tags
                for j in range(len(lista_tags)):
                    if(str(rfid) == lista_tags[j]):
                        if(tempo_leitura - tempo_aux[j] > tempo_minimo):
                            x = {"tag": str(rfid), "tempo": (tempo_leitura - tempo_aux[j])}
                            data = json.dumps(x)
                            enviar_mensagem(conn, data)
                            tempo_aux[j] = tempo_leitura

            atual = 0

            while(final - atual > 0):
                atual = time.time()
                atual -= inicio

            leitor.stop_reading()

            print("Fim da Qualificação\n")

            x = {"tag": "Fim", "tempo": "Fim"}
            data = json.dumps(x)
            enviar_mensagem(conn, data)

if __name__ == '__main__':
    run()