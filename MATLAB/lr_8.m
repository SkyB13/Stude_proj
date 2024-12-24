% Объявление весов
w1 = [0.5 0.5 0.5 0.5;
    0.5 0.5 0.5 0.5;
    0.5 0.5 0.5 0.5;
    0.5 0.5 0.5 0.5;];

w2 = [0.5 0.5 0.5 0.5;];

% Входные данные 
n = 2;
x1_input = linspace(2, 4, n);
x2_input = linspace(4, 6, n);
x3_input = linspace(6, 8, n);
x4_input = linspace(8, 10, n);
[x1, x2] = meshgrid(x1_input, x2_input);
[x3, x4] = meshgrid(x3_input, x4_input);

% Параметры обучения
epoch = 1;
ny = 0.2;
learning_rage_decay = 0.95;
error_value = 0.0001;
previous_error = 0;

sigmoid = @(x) 1./ (1 + exp(-x));

for j = 1 : epoch
    total_error = 0;
    for i = 1 : numel(x1)
        target = (x1(i)^2 * x2(i)) + (x3(i)^2 * x4(i));

        S1 = w1(1, 1) * x1(i) + w1(1, 2) * x2(i) + w1(1, 3) * x3(i) + w1(1, 4) * x4(i);
        S2 = w1(2, 1) * x1(i) + w1(2, 2) * x2(i) + w1(2, 3) * x3(i) + w1(2, 4) * x4(i);
        S3 = w1(3, 1) * x1(i) + w1(3, 2) * x2(i) + w1(3, 3) * x3(i) + w1(3, 4) * x4(i);
        S4 = w1(4, 1) * x1(i) + w1(4, 2) * x2(i) + w1(4, 3) * x3(i) + w1(4, 4) * x4(i);
        y1 = sigmoid(S1);
        y2 = sigmoid(S2);
        y3 = sigmoid(S3);
        y4 = sigmoid(S4);

        % Выходной слой
        s = w2(1, 1) * y1 + w2(1, 2) * y2 + w2(1,3) * y3 + w2(1, 4) * y4;
        y = sigmoid(s);

        % Ошибка
        error = (y - target)^2;

        % Значение градиента выходного слоя
        grad_output = (y - target) * y * (1 - y);

        grad_output_21 = y1 * (1 - y1) * grad_output * w2(1,1);
        grad_output_22 = y2 * (1 - y2) * grad_output * w2(1,2);
        grad_output_23 = y3 * (1 - y3) * grad_output * w2(1,3);
        grad_output_24 = y4 * (1 - y4) * grad_output * w2(1,4);

        % Обновление весов
        w1(1,1) = w1(1,1) - ny * x1(i) * grad_output_21;
        w1(1,2) = w1(1,2) - ny * x1(i) * grad_output_22;
        w1(1,3) = w1(1,3) - ny * x1(i) * grad_output_23;
        w1(1,4) = w1(1,4) - ny * x1(i) * grad_output_24;
        
        w1(2,1) = w1(2,1) - ny * x2(i) * grad_output_21;
        w1(2,2) = w1(2,2) - ny * x2(i) * grad_output_22;
        w1(2,3) = w1(2,3) - ny * x2(i) * grad_output_23;
        w1(2,4) = w1(2,4) - ny * x2(i) * grad_output_24;

        w1(3,1) = w1(3,1) - ny * x3(i) * grad_output_21;
        w1(3,2) = w1(3,2) - ny * x3(i) * grad_output_22;
        w1(3,3) = w1(3,3) - ny * x3(i) * grad_output_23;
        w1(3,4) = w1(3,4) - ny * x3(i) * grad_output_24;

        w1(4,1) = w1(4,1) - ny * x4(i) * grad_output_21;
        w1(4,2) = w1(4,2) - ny * x4(i) * grad_output_22;
        w1(4,3) = w1(4,3) - ny * x4(i) * grad_output_23;
        w1(4,4) = w1(4,4) - ny * x4(i) * grad_output_24;

        w2(1, 1) = w2(1, 1) - ny * y1 * grad_output;
        w2(1, 2) = w2(1, 2) - ny * y2 * grad_output;
        w2(1, 3) = w2(1, 3) - ny * y3 * grad_output;
        w2(1, 4) = w2(1, 4) - ny * y4 * grad_output;
    end

    total_error = total_error / numel(x1);

    % Обновление скорости обучения
    if total_error >= previous_error
        ny = ny * learning_rage_decay;
    end

    previous_error = total_error;

    % Проверка на завершение обучения
    if total_error < error_value
        disp(['Эпоха ' num2str(j) ' значение ошибки ' num2str(total_error) ' обучение окончено']);
        break;
    else
        disp(['Эпоха ' num2str(j) ' значение ошибки ' num2str(total_error)]);
    end
end

disp('Обновленные веса w1: ');
disp(w1);
disp('Обновленные веса w2: ');
disp(w2);