window.addEventListener('load', () => {
  const canvas = document.getElementById('grafico');
  if (!canvas) return;

  const ctx = canvas.getContext('2d');

  if (!ctx) return;

  const chart = new Chart(ctx, {
    type: 'bar',
    data: window.chartData,
    options: {
      responsive: true,
      plugins: {
        legend: {
          position: 'top',
        },
        title: {
          display: true,
          text: 'Estadísticas por campaña',
        },
      },
    },
  });

  window.myChart = chart;
});
