import matplotlib.pyplot as plt
import pandas as pd
from sklearn.cluster import KMeans

# Dados do conjunto de IMC
data = pd.read_csv('imc.txt', sep=';', header=None)

# Mapear classes para números
class_map = {
    'Normal': 0,
    'Peso Baixo': 1,
    'Obesidade I': 2,
    'Obesidade II': 3,
    'Obesidade Mórbida': 4
}

# Converter classes em números
data[5] = data[5].map(class_map)

# Substituir vírgulas por pontos nos valores numéricos
data[1] = data[1].str.replace(',', '.')
data[4] = data[4].str.replace(',', '.')

# Converter colunas 1 e 4 para float
data[1] = data[1].astype(float)
data[4] = data[4].astype(float)

# Lista para armazenar as variâncias dentro dos clusters
variances = []

# Testar diferentes valores de k
for k in range(1, 11):
    kmeans = KMeans(n_clusters=k, random_state=42, n_init=10)
    kmeans.fit(data.iloc[:, 1:5])  # Selecionando as colunas 1 a 4 (altura, peso, idade e IMC)
    variances.append(kmeans.inertia_)  # Inércia = soma das distâncias quadráticas dentro dos clusters

# Plotar gráfico do método do cotovelo
plt.plot(range(1, 11), variances, marker='o')
plt.xlabel('Número de clusters (k)')
plt.ylabel('Variância')
plt.title('Método do Cotovelo')
plt.show()
