% Входные данные (вариант №3)
x = [0 0; 1 1; -1 1; -1 0]; 
target = [0; 0; 1; 1]; % Целевые значения
weight = [1 -0.8]; % Начальные веса
offset = [1]; % Начальное смещение

% Параметры обучения
max_error = 0.01; % Максимальная ошибка
learning_rate = 0.1; % Скорость обучения
epoch = 10; % Кол-во эпох

% Функция активации
linear_activation = @(z) z;

for e = 1 : epoch
    for i = 1 : size(x, 1)
        new_input = weight * x(i, :)' + offset;
        output = linear_activation(new_input);

        error = target(i) - output;

        if abs(error) < max_error
            continue;
        end

        weight = weight + learning_rate * error * x(i, :);
        offset = offset + learning_rate * error;
    end
end

% Итоговые веса и смещение
disp('Итоговые веса: ');
disp(weight);
disp('Итоговое смещение: ');
disp(offset);
