%laba_1 task_1
% Определение критериев и рангов
criteria = {'Качество зерна', 'Цена зерна', 'Транспортные расходы', ...
           'Форма оплаты', 'Минимальная партия', 'Надежность поставки'};
initial_ranks = [1, 3, 6, 4, 2, 5];

% Создание матрицы парных сравнений
n_criteria = length(criteria);
pair_matrix = zeros(n_criteria);

for i = 1:n_criteria
    for j = i+1:n_criteria
        pair_matrix(i,j) = initial_ranks(i) / initial_ranks(j);
        pair_matrix(j,i) = initial_ranks(j) / initial_ranks(i);
    end
end

% Вычисление итерированной силы критерия
eigenvector_result = eigenvector(pair_matrix);

% Оценка значимости критериев
[sorted_eigenvalues, indices] = sort(eigenvector_result, 'descend');
sorted_criteria = criteria(indices);

fprintf('Ранжирование критериев по важности:\n');
for i = 1:n_criteria
    fprintf('%d. %s (%.4f)\n', i, sorted_criteria{i}, sorted_eigenvalues(i));
end

%Построение графика по значимости критериев
figure;
bar(eigenvector_result);
title('Оценка значимости критериев');
xlabel('Критерии');
ylabel('Значимость');
xticks(1:6);
xticklabels({'Качество зерна', 'Цена зерна', 'Транспортные расходы', ...
           'Форма оплаты', 'Минимальная партия', 'Надежность поставки'})


% Функция для вычисления собственного вектора
function result = eigenvector(matrix)
    n = size(matrix, 1);
    v = ones(n, 1);
    
    for k = 1:3 % Максимальное количество итераций
        v_new = matrix * v / sum(matrix * v);
        
        if norm(v_new - v) < 1e-6 % Условие сходимости
            break;
        end
        
        v = v_new;
    end
    
    result = v / sum(v); % Нормализация
end