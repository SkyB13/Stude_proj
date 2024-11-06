% Данные о характеристиках двигателей
engines = [
    30, 40, 20;% Двигатель 1: Мощность, Крутящий момент, Масса
    25, 20, 30;% Двигатель 2
    40, 45, 54;% Двигатель 3
    28, 30, 35;% Двигатель 4
    15, 12, 20;% Двигатель 5
    50, 30, 40% Двигатель 6
];


% Основной код
n = size(engines, 1);

% Генерируем матрицы парных сравнений для каждого критерия
matrix_power = generate_comparison_matrix(engines, 1);  % Мощность
matrix_torque = generate_comparison_matrix(engines, 2);  % Крутящий момент
matrix_mass = generate_comparison_matrix(engines, 3);    % Масса


weights_power = calculate_weights(matrix_power);
weights_torque = calculate_weights(matrix_torque);
weights_mass = calculate_weights(matrix_mass);

disp('Мощность:'); disp(weights_power);
disp('Крутящий момент:'); disp(weights_torque);
disp('Масса:'); disp(weights_mass);

final_scores = zeros(n, 1);
for i = 1 : n
    final_scores(i) = ...
        weights_power(i) + ... 
        weights_torque(i) + ... 
        weights_mass(i);
end

disp("Оценка каждого двигателя:"); disp(final_scores);

[max_score, best] = max(final_scores);
fprintf('Оптимальный вариант: Двигатель %d с весом: %.4f\n', best, max_score);

figure;
bar(final_scores);
xlabel('Варианты двигателей');
ylabel('Итоговый вес');
title('Итоговые веса вариантов двигателей');
xticklabels({'Двиг_1', 'Двиг_2', 'Двиг_3', 'Двиг_4', 'Двиг_5', 'Двиг_6'});
grid on;

% Функция для вычисления весов из матрицы парных сравнений
function [normalized_weights] = calculate_weights(matrix)
    n = size(matrix, 1);
    row_products = prod(matrix, 2);
    row_n_products = nthroot(row_products, n);
    total_sum = sum(row_n_products);
    normalized_weights = row_n_products / total_sum;
end

% Функция для генерации матрицы парных сравнений на основе реальных данных
function matrix = generate_comparison_matrix(data, criterion_index)
    n = size(data, 1);
    matrix = zeros(n);
    
    % Заполняем диагональ единицами
    for i = 1:n
        matrix(i,i) = 1;
    end
    
    % Заполняем верхнюю часть треугольника
    for i = 1:n
        for j = i+1:n
            % Для мощности и крутящего момента большее значение лучше
            if criterion_index == 1 || criterion_index == 2
                ratio = data(i, criterion_index) / data(j, criterion_index);
            % Для массы меньшее значение лучше
            else
                ratio = data(j, criterion_index) / data(i, criterion_index);
            end
            
            matrix(i,j) = ratio;
            matrix(j,i) = 1/ratio;
        end
    end
end
