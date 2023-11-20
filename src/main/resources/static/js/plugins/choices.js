const choices = document.querySelectorAll('.select-picker');

  if (choices) {
    choices.forEach(choice => {
      const sortingchoices = new Choices(choice, {
        placeholder: true,
        searchPlaceholderValue: '',
        itemSelectText: '',
        loadingText: 'Cargando...',
        noResultsText: 'No se encontraron resultados',
        noChoicesText: 'No hay opciones para elegir',
        itemSelectText: 'Seleccione',
      });
  
    });
  }