import matplotlib.pyplot as plt
import pandas as pd
from sklearn.cluster import KMeans

# Dados do conjunto de plantas Iris
data = pd.read_csv('iris.data', header=None)

# Mapear classes para números
class_map = {
    'Iris-setosa': 0,
    'Iris-versicolor': 1,
    'Iris-virginica': 2
}

# Converter classes em números
data[4] = data[4].map(class_map)

# Lista para armazenar as variâncias dentro dos clusters
variances = []

# Testar diferentes valores de k
for k in range(1, 11):
    kmeans = KMeans(n_clusters=k, random_state=42, n_init=10)
    kmeans.fit(data.iloc[:, :-1])  # Excluindo a última coluna (classes) dos dados de entrada
    variances.append(kmeans.inertia_)  # Inércia = soma das distâncias quadráticas dentro dos clusters

# Plotar gráfico do método do cotovelo
plt.plot(range(1, 11), variances, marker='o')
plt.xlabel('Número de clusters (k)')
plt.ylabel('Variância')
plt.title('Método do Cotovelo')
plt.show()
