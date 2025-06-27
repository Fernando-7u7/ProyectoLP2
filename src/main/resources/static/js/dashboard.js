const ventas = window.ventasUltimos6Meses || [];

const labels = ventas.map(v => {
  const [year, month] = v.mes.split('-');
  const date = new Date(year, month - 1);
  let mes = new Intl.DateTimeFormat('es-ES', { month: 'long' }).format(date);
  return mes.charAt(0).toUpperCase() + mes.slice(1);
});


const data = ventas.map(v => Number(v.totalVendido).toFixed(2));
const ctx = document.getElementById('ventasChart').getContext('2d');

const ventasChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: labels,
    datasets: [{
      label: 'Ventas por mes',
      data: data,
      backgroundColor: '#00ae8b',
      borderColor: '#00ae8b',
      borderWidth: 1
    }]
  },
  options: {
    responsive: true,
    scales: {
      y: {
        beginAtZero: true,
        ticks: {
          callback: function(value) {
            return 'S/. ' + Number(value).toFixed(2);
          }
        }
      }
    },
    plugins: {
      tooltip: {
        callbacks: {
          label: function(context) {
			return 'S/. ' + Number(context.parsed.y).toFixed(2);
          }
        }
      }
    }
  }
});
